package com.niq_dev.portal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.niq_dev.portal.dto.AuthorityDto;
import com.niq_dev.portal.dto.RoleDto;
import com.niq_dev.portal.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IamService {
	
	private final WebClient webClient;
	private final OAuthClientService oAuthClientService;

    private static final String CLIENT_ID = "portal-client";

	public List<UserDto> getUsers(OAuth2AuthenticationToken authentication) {
    	
    	List<UserDto> result = new ArrayList<>();

        OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(authentication, CLIENT_ID);
        if (client == null || client.getAccessToken() == null) return result;
        
        String accessToken = client.getAccessToken().getTokenValue();
        if (accessToken.isBlank()) return result;
        
    	String uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api/v1/users").toUriString();
    	ParameterizedTypeReference<List<UserDto>> typeRef = new ParameterizedTypeReference<>() {};
    	
    	result = webClient.get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();
    	
        return result;
	}

	public List<RoleDto> getRoles(OAuth2AuthenticationToken authentication) {

    	List<RoleDto> result = new ArrayList<>();
    	
        OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(authentication, CLIENT_ID);
        if (client == null || client.getAccessToken() == null) return result;
        
        String accessToken = client.getAccessToken().getTokenValue();
        if (accessToken.isBlank()) return result;
        
    	String uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api/v1/roles").toUriString();
    	ParameterizedTypeReference<List<RoleDto>> typeRef = new ParameterizedTypeReference<>() {};
    	
    	result = webClient.get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();
	    	
        return result;
	}

	public List<AuthorityDto> getAuthorities(OAuth2AuthenticationToken authentication) {

    	List<AuthorityDto> result = new ArrayList<>();
    	
        OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(authentication, CLIENT_ID);
        if (client == null || client.getAccessToken() == null) return result;
        
        String accessToken = client.getAccessToken().getTokenValue();
        if (accessToken.isBlank()) return result;

    	String uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api/v1/authorities").toUriString();
    	ParameterizedTypeReference<List<AuthorityDto>> typeRef = new ParameterizedTypeReference<>() {};
    	
    	result = webClient.get()
                    .uri(uri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(typeRef)
                    .block();
    	
        return result;
	}

}
