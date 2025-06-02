package com.niq_dev.portal.controller;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.niq_dev.portal.dto.AppSystemDto;
import com.niq_dev.portal.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping("/admin")
	public String home(Model model, OAuth2AuthenticationToken authentication) {
		
		List<AppSystemDto> systems = adminService.getSystems(authentication);
		model.addAttribute("systems", systems);

	    return "admin";
	}
	
}
