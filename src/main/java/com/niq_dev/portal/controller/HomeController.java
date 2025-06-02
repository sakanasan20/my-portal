package com.niq_dev.portal.controller;

import java.util.Arrays;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		if (oidcUser != null) {
			if (oidcUser.getClaimAsString("systems") != null) {
				model.addAttribute("systems", Arrays.asList(oidcUser.getClaimAsString("systems").split(" ")));
			}
			if (oidcUser.getClaimAsString("modules") != null) {
				model.addAttribute("modules", Arrays.asList(oidcUser.getClaimAsString("modules").split(" ")));
			}
			if (oidcUser.getClaimAsString("features") != null) {
				model.addAttribute("features", Arrays.asList(oidcUser.getClaimAsString("features").split(" ")));
			}
		}
		model.addAttribute("user", oidcUser);

	    return "home";
	}
	
}
