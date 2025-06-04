package com.niq_dev.portal.config.oauth2;

import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 一個包裝型的 OAuth2AuthorizedClientProvider，用於攔截 refresh token 的使用情況。
 * 主要目的是在 access token 被成功刷新時，記錄相關資訊（例如誰刷新了 token）。
 */
@Slf4j
@RequiredArgsConstructor
public class LoggingRefreshTokenAuthorizedClientProvider implements OAuth2AuthorizedClientProvider {

    /**
     * 實際負責 refresh token 授權邏輯的委派 provider。
     * 例如：OAuth2AuthorizedClientProviderBuilder.builder().refreshToken().build()
     */
    private final OAuth2AuthorizedClientProvider delegate;

    /**
     * 用來記錄 refresh token 使用記錄的元件，可自定義邏輯，例如寫 log、記錄 DB 等。
     */
    private final LoggingRefreshTokenUsageTracker tracker;

    /**
     * 授權流程主方法，會呼叫內部的 delegate 進行授權。
     * 如果 refresh 成功（新的 access token 不同於舊的），會記錄 refresh 行為。
     *
     * @param context 授權所需的上下文資訊
     * @return 新的或原本的 OAuth2AuthorizedClient，若無法授權則為 null
     */
    @Override
    public OAuth2AuthorizedClient authorize(OAuth2AuthorizationContext context) {
        // 委派給原本的 refreshToken 授權 provider
        OAuth2AuthorizedClient client = delegate.authorize(context);

        // 檢查是否真的刷新了 token
        if (client != null && context.getAuthorizedClient() != null) {
        	log.info("Access token refreshed for client: {}, principal: {}", 
        			client.getClientRegistration().getRegistrationId(),
        			client.getPrincipalName());
            boolean isRefreshed = !client.getAccessToken().getTokenValue()
                    .equals(context.getAuthorizedClient().getAccessToken().getTokenValue());

            // 如果 access token 有更新（代表有 refresh），記錄這次 refresh 行為
            if (isRefreshed) {
                tracker.recordRefresh(
                        client.getPrincipalName(),                       // 使用者名稱
                        client.getClientRegistration().getRegistrationId() // OAuth2 client ID
                );
            }
        }

        return client;
    }
}
