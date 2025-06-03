package com.niq_dev.portal.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final WebClient webClient;
	private final OAuthClientService oAuthClientService;
	private final OAuth2AuthorizedClientService authorizedClientService;
	
	private static final String CLIENT_ID = "portal-client";
	
	public void revokeToken(OAuth2AuthenticationToken authentication) {
		
		OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(authentication, CLIENT_ID);
		
		String accessToken = client.getAccessToken().getTokenValue();
        if (accessToken.isBlank()) return;
		
        String refreshToken = client.getRefreshToken().getTokenValue();
        if (refreshToken.isBlank()) return;
		
		if (client != null && client.getRefreshToken() != null) {
				revokeRefreshToken(accessToken, refreshToken);
				authorizedClientService.removeAuthorizedClient(CLIENT_ID, authentication.getName());
		}
	}

	private void revokeRefreshToken(String accessToken, String refreshToken) {
	    
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("token", refreshToken);
	    params.add("token_type_hint", "refresh_token");
		        
    	String uri = UriComponentsBuilder.fromUriString("http://localhost:8081/oauth2/revoke").toUriString();
    	
    	webClient.post()
                .uri(uri)
                .header("Authorization", "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    	
        return;
	}
    
}
