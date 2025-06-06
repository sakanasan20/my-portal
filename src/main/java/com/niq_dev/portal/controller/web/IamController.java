package com.niq_dev.portal.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/iam")
@RequiredArgsConstructor
public class IamController {
	
	@GetMapping
	public String index() {
	    return "iam/index";
	}
	
}
