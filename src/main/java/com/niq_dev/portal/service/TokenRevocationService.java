package com.niq_dev.portal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

	private final WebClient webClient;

	private static final String CLIENT_ID = "portal-client";
	private static final String CLIENT_SECRET = "portal-secret";

	public Mono<Void> revokeRefreshToken(String refreshToken) {
		return webClient.post()
				.uri("http://localhost:8081/oauth2/revoke")
				.headers(headers -> {
						headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
						headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
					})
				.body(BodyInserters
						.fromFormData("token", refreshToken)
						.with("token_type_hint", "refresh_token"))
				.retrieve()
				.bodyToMono(Void.class);
	}

}
