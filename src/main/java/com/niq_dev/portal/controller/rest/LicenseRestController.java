package com.niq_dev.portal.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.LicenseDto;
import com.niq_dev.portal.service.LicenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LicenseRestController {

	private final LicenseService licenseService;
	
	@GetMapping("/license/licenses")
	public List<LicenseDto> getLicenses() {
	    return licenseService.getLicenses();
	}
	
}
