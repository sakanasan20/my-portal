package com.niq_dev.portal.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthClientService {

	private final OAuth2AuthorizedClientManager authorizedClientManager;

	/**
	 * 根據目前使用者的 OAuth2AuthenticationToken 以及指定的 clientRegistrationId， 取得對應的
	 * OAuth2AuthorizedClient（包含 Access Token）。
	 *
	 * @param authentication       已登入的 OAuth2AuthenticationToken，代表目前使用者的認證狀態
	 * @param clientRegistrationId OAuth2 Client Registration Id（例如 "portal-client"）
	 * @return 有效的 OAuth2AuthorizedClient 實例
	 * @throws ResponseStatusException 當無法取得授權或 token 過期時拋出 401 Unauthorized
	 */
	public OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication,
			String clientRegistrationId) {
		
		// 若輸入參數有誤，直接拋出未授權異常
		if (authentication == null || clientRegistrationId == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized request");
		}

		// 建立授權請求物件，使用 principal(使用者認證)和 clientRegistrationId
		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
				.principal(authentication).build();

		try {
			// 利用 authorizedClientManager 執行授權流程(含檢查、刷新 Token)
			OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);

			// 若取得的 client 為 null，表示需要重新登入或授權
			// 或者 access token 不存在（可能過期），一樣拋出未授權例外
			if (client == null || client.getAccessToken() == null) {
				log.info("Client 不存在 或 access token 不存在: {}", authentication.getName());
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access token is missing or expired");
			}
			return client;

		} catch (ClientAuthorizationRequiredException ex) {
			log.info("Token refresh failed or token expired for principal: {}", authentication.getName(), ex);
			// 當系統判斷需要使用者重新授權時，捕獲此例外並回傳 401
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Client authorization required", ex);
		}
	}
}
