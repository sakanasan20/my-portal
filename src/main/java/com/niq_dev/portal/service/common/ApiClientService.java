package com.niq_dev.portal.service.common;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiClientService {
	
	@Value("${oauth2.client-id}")
	private String clientId;
	
    private final WebClient webClient;
    private final OAuthClientService oAuthClientService;

    public <T> List<T> fetchData(String url, ParameterizedTypeReference<List<T>> typeRef) {
        // 從 SecurityContextHolder 拿認證物件
        var authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            // 使用者未登入或不是 OAuth2 登入
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: OAuth2 authentication required");
        }
    	
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
