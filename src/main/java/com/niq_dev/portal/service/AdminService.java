package com.niq_dev.portal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.AppSystemDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final WebClient webClient;
    private final OAuth2AuthorizedClientService authorizedClientService;

	public List<AppSystemDto> getSystems(OAuth2AuthenticationToken authentication) {

    	OAuth2AuthorizedClient client = null;
    	List<AppSystemDto> result = new ArrayList<>();
    	
    	System.out.println("(authentication != null): " + (authentication != null));
    	if (authentication != null) {
    		client = authorizedClientService.loadAuthorizedClient("portal-client", authentication.getName());
    	}
    	System.out.println("(client != null): " + (client != null));
    	if (client != null) {
	    	UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8081/api/v1/systems");
	    	String uri = uriBuilder.toUriString();
	    	String accessToken = client.getAccessToken().getTokenValue();
	    	ParameterizedTypeReference<List<AppSystemDto>> typeRef = new ParameterizedTypeReference<>() {};
	    	
	    	if (accessToken.isBlank()) return result;
	    	
	    	result = webClient.get()
	                    .uri(uri)
	                    .header("Authorization", "Bearer " + accessToken)
	                    .retrieve()
	                    .bodyToMono(typeRef)
	                    .block();
	    	System.out.println("result: " + result);
    	}
        return result;
	}
	
	
	
}
