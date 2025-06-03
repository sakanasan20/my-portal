package com.niq_dev.portal.controller;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.AuthorityDto;
import com.niq_dev.portal.dto.RoleDto;
import com.niq_dev.portal.dto.UserDto;
import com.niq_dev.portal.service.IamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IamRestController {

	private final IamService iamService;
	
	@GetMapping("/iam/users")
	public List<UserDto> getUsers(OAuth2AuthenticationToken authentication) {
	    return iamService.getUsers(authentication);
	}
	
	@GetMapping("/iam/roles")
	public List<RoleDto> getRoles(OAuth2AuthenticationToken authentication) {
	    return iamService.getRoles(authentication);
	}
	
	@GetMapping("/iam/authorities")
	public List<AuthorityDto> getAuthorities(OAuth2AuthenticationToken authentication) {
	    return iamService.getAuthorities(authentication);
	}
	
}
