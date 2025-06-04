package com.niq_dev.portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Token 作廢服務
 */
@Service
@RequiredArgsConstructor
public class TokenRevocationService {

	private final WebClient webClient;

	@Value("${oauth2.auth-server-url}")
    private String authServerUrl;
	
	@Value("${oauth2.client-id}")
	private String clientId;
	
	@Value("${oauth2.client-secret}")
	private String clientSecret;

	/**
	 * 作廢 Refresh Token
	 * @param refreshToken
	 * @return
	 */
	public Mono<Void> revokeRefreshToken(String refreshToken) {
		
		String uri = UriComponentsBuilder
		        .fromUriString(authServerUrl)
		        .path("/oauth2/revoke")
		        .toUriString();
		
		return webClient.post()
				.uri(uri)
				.headers(headers -> {
						headers.setBasicAuth(clientId, clientSecret);
						headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
					})
				.body(BodyInserters
						.fromFormData("token", refreshToken)
						.with("token_type_hint", "refresh_token"))
				.retrieve()
				.bodyToMono(Void.class);
	}

}
