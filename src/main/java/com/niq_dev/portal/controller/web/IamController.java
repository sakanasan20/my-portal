package com.niq_dev.portal.controller.web;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IamController {
	
	@GetMapping("/iam")
	public String home(Model model, OAuth2AuthenticationToken authentication) {
	    return "iam";
	}
	
}
