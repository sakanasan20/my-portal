package com.niq_dev.portal.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.iam.AuthorityDto;
import com.niq_dev.portal.dto.iam.RoleDto;
import com.niq_dev.portal.dto.iam.UserDto;
import com.niq_dev.portal.service.IamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/iam/rest")
@RequiredArgsConstructor
public class IamRestController {

	private final IamService iamService;
	
	@GetMapping("/users")
	public List<UserDto> getUsers() {
	    return iamService.getUsers();
	}
	
	@GetMapping("/roles")
	public List<RoleDto> getRoles() {
	    return iamService.getRoles();
	}
	
	@GetMapping("/authorities")
	public List<AuthorityDto> getAuthorities() {
	    return iamService.getAuthorities();
	}
	
}
