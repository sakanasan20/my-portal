package com.niq_dev.portal.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiClientService {
	
	@Value("${oauth2.client-id}")
	private String clientId;
	
    private final WebClient webClient;
    private final OAuthClientService oAuthClientService;

    public <T> List<T> fetchData(OAuth2AuthenticationToken authentication, String url, ParameterizedTypeReference<List<T>> typeRef) {
        OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(authentication, clientId);
        if (client == null || client.getAccessToken() == null) return Collections.emptyList();

        String accessToken = client.getAccessToken().getTokenValue();
        if (accessToken.isBlank()) return Collections.emptyList();

        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(typeRef)
                .blockOptional()
                .orElse(Collections.emptyList());
    }
    
}
