package com.niq_dev.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.iam.AuthorityDto;
import com.niq_dev.portal.dto.iam.RoleDto;
import com.niq_dev.portal.dto.iam.UserDto;
import com.niq_dev.portal.service.common.ApiClientService;
import com.niq_dev.portal.service.common.ApiPaths;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IamService {
	
	@Value("${oauth2.api-base-url}")
    private String apiBaseUrl;
	
    private final ApiClientService apiClientService;

    private String usersUrl;
    private String rolesUrl;
    private String authoritiesUrl;

    @PostConstruct
    public void init() {
        this.usersUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .path(ApiPaths.USERS.getPath())
                .toUriString();

        this.rolesUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .path(ApiPaths.ROLES.getPath())
                .toUriString();

        this.authoritiesUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .path(ApiPaths.AUTHORITIES.getPath())
                .toUriString();
    }

    public List<UserDto> getUsers() {
        return apiClientService.fetchData(usersUrl, new ParameterizedTypeReference<List<UserDto>>() {});
    }

    public List<RoleDto> getRoles() {
        return apiClientService.fetchData(rolesUrl, new ParameterizedTypeReference<List<RoleDto>>() {});
    }

    public List<AuthorityDto> getAuthorities() {
        return apiClientService.fetchData(authoritiesUrl, new ParameterizedTypeReference<List<AuthorityDto>>() {});
    }

}
