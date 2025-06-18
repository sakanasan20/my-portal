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

    public <T> List<T> fetchList(String url, ParameterizedTypeReference<List<T>> typeRef) {
        return authorizedGet(url)
                .retrieve()
                .bodyToMono(typeRef)
                .blockOptional()
                .orElse(Collections.emptyList());
    }
	
	public <T, R> R post(String url, T body, Class<R> responseType) {
	    return authorizedPost(url)
	            .bodyValue(body)
	            .retrieve()
	            .bodyToMono(responseType)
	            .block();
	}
	
	private String getAccessToken() {
	    var authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (!(authentication instanceof OAuth2AuthenticationToken oauth)) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "OAuth2 authentication required");
	    }

	    OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(oauth, clientId);
	    if (client == null || client.getAccessToken() == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No valid OAuth2 access token");
	    }

	    String token = client.getAccessToken().getTokenValue();
	    if (token.isBlank()) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty access token");
	    }

	    return token;
	}
	
	private WebClient.RequestHeadersSpec<?> authorizedGet(String url) {
	    return webClient.get()
	            .uri(url)
	            .header("Authorization", "Bearer " + getAccessToken());
	}

	private WebClient.RequestBodySpec authorizedPost(String url) {
	    return webClient.post()
	            .uri(url)
	            .header("Authorization", "Bearer " + getAccessToken());
	}
}
