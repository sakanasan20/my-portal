package com.niq_dev.portal.controller;

import java.time.ZonedDateTime;
import java.util.Arrays;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

	@GetMapping("/profile")
	public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		if (oidcUser != null) {
			if (oidcUser.getClaimAsMap("userinfo") != null) {
				model.addAttribute("userinfo", oidcUser.getClaimAsMap("userinfo"));
				ZonedDateTime createdAt = ZonedDateTime.parse(oidcUser.getClaimAsMap("userinfo").get("createdAt").toString());
				model.addAttribute("createdAt", createdAt);
			}
			if (oidcUser.getClaimAsString("roles") != null) {
				model.addAttribute("roles", Arrays.asList(oidcUser.getClaimAsString("roles").replaceAll("ROLE_", "").split(" ")));
			}
			if (oidcUser.getClaimAsString("authorities") != null) {
				model.addAttribute("authorities", Arrays.asList(oidcUser.getClaimAsString("authorities").split(" ")));
			}
		}
		
	    return "profile";
	}
	
}
