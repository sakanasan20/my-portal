package com.niq_dev.portal.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/scm")
@RequiredArgsConstructor
public class ScmController {
	
	@GetMapping
	public String scm(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/main");
	    return "scm";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
	    model.addAttribute("contentFragment", "fragments/scm/dashboard");
	    return "scm";
	}

	@GetMapping("/orders")
	public String orders(Model model) {
	    model.addAttribute("contentFragment", "fragments/scm/orders");
	    return "scm";
	}
	
	@GetMapping("/inventory")
	public String inventory(Model model) {
	    model.addAttribute("contentFragment", "fragments/scm/inventory");
	    return "scm";
	}
	
	@GetMapping("/suppliers")
	public String suppliers(Model model) {
	    model.addAttribute("contentFragment", "fragments/scm/suppliers");
	    return "scm";
	}
	
	@GetMapping("/reports")
	public String reports(Model model) {
	    model.addAttribute("contentFragment", "fragments/scm/reports");
	    return "scm";
	}
}
