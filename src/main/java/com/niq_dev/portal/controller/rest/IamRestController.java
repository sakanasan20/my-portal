package com.niq_dev.portal.controller.rest;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niq_dev.portal.dto.UserFormData;
import com.niq_dev.portal.dto.iam.AuthorityDto;
import com.niq_dev.portal.dto.iam.RoleDto;
import com.niq_dev.portal.dto.iam.UserDto;
import com.niq_dev.portal.service.IamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/iam/rest")
@RequiredArgsConstructor
public class IamRestController {

	private final IamService iamService;
	
	@GetMapping("/users")
	public List<UserDto> getUsers() {
	    return iamService.getUsers();
	}
	
    @PostMapping("/users")
    public UserDto createUser(Model model, OAuth2AuthenticationToken authentication, @ModelAttribute UserFormData formData) {
    	log.info(formData.toString());
        return iamService.createUser(formData);
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
