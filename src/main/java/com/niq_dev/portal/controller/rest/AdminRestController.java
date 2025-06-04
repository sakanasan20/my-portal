package com.niq_dev.portal.controller.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.admin.AppModuleDto;
import com.niq_dev.portal.dto.admin.AppSystemDto;
import com.niq_dev.portal.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminRestController {

	private final AdminService adminService;
	
	@GetMapping("/admin/systems")
	public List<Map<String, Object>> getSystems() {
		List<AppSystemDto> systems = adminService.getSystems();

		List<Map<String, Object>> flatList = new ArrayList<>();
		
		systems.stream().forEach(system -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", system.getCode());
			map.put("name", system.getName());
			map.put("description", system.getDescription());
			map.put("parentCode", null);
			map.put("id", system.getCode().replace(":", "_").replace(".", "_"));
			map.put("pid", null);
	        flatList.add(map);

		});
		
		systems.stream().map(AppSystemDto::getModules).flatMap(Collection::stream).forEach(module -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", module.getCode());
			map.put("name", module.getName());
			map.put("description", module.getDescription());
			map.put("parentCode", module.getParentCode());
			map.put("id", module.getCode().replace(":", "_").replace(".", "_"));
			map.put("pid", module.getParentCode().replace(":", "_").replace(".", "_"));
	        flatList.add(map);
		});
		
		systems.stream().map(AppSystemDto::getModules).flatMap(Collection::stream).map(AppModuleDto::getFeatures).flatMap(Collection::stream).forEach(feature -> {
			Map<String, Object> map = new HashMap<>();
			map.put("code", feature.getCode());
			map.put("name", feature.getName());
			map.put("description", feature.getDescription());
			map.put("parentCode", feature.getParentCode());
			map.put("id", feature.getCode().replace(":", "_").replace(".", "_"));
			map.put("pid", feature.getParentCode().replace(":", "_").replace(".", "_"));
	        flatList.add(map);
		});

	    return flatList;
	}
	
}
