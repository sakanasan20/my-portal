package com.niq_dev.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.LicenseDto;
import com.niq_dev.portal.service.common.ApiClientService;
import com.niq_dev.portal.service.common.ApiPaths;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicenseService {

	@Value("${oauth2.api-base-url}")
    private String apiBaseUrl;
	
    private final ApiClientService apiClientService;

    private String licensesUrl;
    
    @PostConstruct
    public void init() {
        this.licensesUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .path(ApiPaths.LICENSES.getPath())
                .toUriString();
    }

	public List<LicenseDto> getLicenses() {
		return apiClientService.fetchList(licensesUrl, new ParameterizedTypeReference<List<LicenseDto>>() {});
	}
	
}
