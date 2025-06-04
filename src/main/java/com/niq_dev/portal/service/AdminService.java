package com.niq_dev.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.AppSystemDto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
    @Value("${oauth2.api-base-url}")
    private String apiBaseUrl;

	private final ApiClientService apiClientService;
	
	private String systemsUrl;
	
    @PostConstruct
    public void init() {
        this.systemsUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .path("/api/v1/systems")
                .toUriString();
    }

	public List<AppSystemDto> getSystems(OAuth2AuthenticationToken authentication) {
		return apiClientService.fetchData(authentication, systemsUrl, new ParameterizedTypeReference<List<AppSystemDto>>() {});
	}

}
