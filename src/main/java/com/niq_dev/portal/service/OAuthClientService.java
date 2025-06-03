package com.niq_dev.portal.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthClientService {

	private final OAuth2AuthorizedClientManager authorizedClientManager;

	public OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication,
			String clientRegistrationId) {
		if (authentication == null)
			return null;

		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
				.principal(authentication).build();

		return authorizedClientManager.authorize(authorizeRequest);
	}
	
}
