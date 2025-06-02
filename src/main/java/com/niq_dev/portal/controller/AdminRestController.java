package com.niq_dev.portal.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.AppModuleDto;
import com.niq_dev.portal.dto.AppSystemDto;
import com.niq_dev.portal.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminRestController {

	private final AdminService adminService;
	
	@GetMapping("/admin/systems")
	public List<Map<String, Object>> getSystems(OAuth2AuthenticationToken authentication) {
		List<AppSystemDto> systems = adminService.getSystems(authentication);
		System.out.println("systems: " + systems);
		List<Map<String, Object>> flatList = new ArrayList<>();
		systems.stream().forEach(system -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", system.getCode());
			map.put("name", system.getName());
			map.put("description", system.getDescription());
			map.put("parentCode", null);
	        flatList.add(map);
	        System.out.println(system);
		});
		systems.stream().map(AppSystemDto::getModules).flatMap(Collection::stream).forEach(module -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", module.getCode());
			map.put("name", module.getName());
			map.put("description", module.getDescription());
			map.put("parentCode", module.getParentCode());
	        flatList.add(map);
			System.out.println(module);
		});
		systems.stream().map(AppSystemDto::getModules).flatMap(Collection::stream).map(AppModuleDto::getFeatures).flatMap(Collection::stream).forEach(feature -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", feature.getCode());
			map.put("name", feature.getName());
			map.put("description", feature.getDescription());
			map.put("parentCode", feature.getParentCode());
	        flatList.add(map);
			System.out.println(feature);
		});
		System.out.println("flatList: " + flatList);
	    return flatList;
	}
	
}
