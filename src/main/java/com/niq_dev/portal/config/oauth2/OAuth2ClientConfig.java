package com.niq_dev.portal.config.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class OAuth2ClientConfig {

    /**
     * 建立並配置 OAuth2AuthorizedClientManager，負責管理 OAuth2 client 授權流程與 token 管理。
     * 
     * @param clientRegistrationRepository 用於查詢 OAuth2 client 註冊資訊
     * @param authorizedClientService 負責儲存與讀取已授權的 client 資訊(包含 token)
     * @param tracker 自訂的刷新 Token 事件追蹤器，用於記錄 token 刷新行為
     * @return 已設定好 OAuth2AuthorizedClientProvider 的 AuthorizedClientManager 實例
     */
    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                          OAuth2AuthorizedClientService authorizedClientService, 
                                                          LoggingRefreshTokenUsageTracker tracker) {

        // 建立一個自訂的 LoggingRefreshTokenAuthorizedClientProvider，
        // 包裝原本的 refreshToken provider，讓我們能在 token 刷新時額外紀錄或執行其他動作
        OAuth2AuthorizedClientProvider refreshProvider = new LoggingRefreshTokenAuthorizedClientProvider(
                OAuth2AuthorizedClientProviderBuilder.builder()
                    .refreshToken() // 只處理 refresh token 流程
                    .build(),
                tracker // 傳入自訂的追蹤器
        );

        // 組合一個完整的 OAuth2AuthorizedClientProvider，支持 authorization_code 授權流程
        // 並使用自訂包裝過的 refresh token provider 取代預設的 refresh token 處理
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()  // 支援 authorization code flow
                        .provider(refreshProvider) // 使用包裝的 refresh token provider
                        .build();

        // 建立 AuthorizedClientManager，將 client 註冊資訊與已授權 client 服務注入
        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientService
                );

        // 將前面建立的 provider 設定到 manager 中，管理 token 授權流程
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

}
