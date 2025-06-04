package com.niq_dev.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.AuthorityDto;
import com.niq_dev.portal.dto.RoleDto;
import com.niq_dev.portal.dto.UserDto;

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

    public List<UserDto> getUsers(OAuth2AuthenticationToken authentication) {
        return apiClientService.fetchData(authentication, usersUrl, new ParameterizedTypeReference<List<UserDto>>() {});
    }

    public List<RoleDto> getRoles(OAuth2AuthenticationToken authentication) {
        return apiClientService.fetchData(authentication, rolesUrl, new ParameterizedTypeReference<List<RoleDto>>() {});
    }

    public List<AuthorityDto> getAuthorities(OAuth2AuthenticationToken authentication) {
        return apiClientService.fetchData(authentication, authoritiesUrl, new ParameterizedTypeReference<List<AuthorityDto>>() {});
    }

}
