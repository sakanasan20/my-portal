package com.niq_dev.portal.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LicenseController {
	
	@GetMapping("/license")
	public String license() {
	    return "license";
	}
	
}
