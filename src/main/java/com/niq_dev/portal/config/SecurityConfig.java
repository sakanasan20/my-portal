package com.niq_dev.portal.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.niq_dev.portal.service.common.OAuthClientService;
import com.niq_dev.portal.service.common.TokenRevocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
    @Value("${oauth2.logout-redirect-uri}")
    private String logoutRedirectUri;
    
	@Value("${oauth2.client-id}")
	private String clientId;
    
    private final TokenRevocationService tokenRevocationService;
    private final OAuthClientService oAuthClientService;

    @Bean
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http,
    	    OAuth2AuthorizedClientRepository authorizedClientRepository) throws Exception {
    	
        http
            .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/resources/**", "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
        		.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessHandler(customLogoutSuccessHandler(authorizedClientRepository))
    		)
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true)); // 啟用 OAuth2 login
        
        return http.build();
    }
    
    private LogoutSuccessHandler customLogoutSuccessHandler(
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        return (request, response, authentication) -> {
            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient(oauthToken, clientId);

                // 作廢 refresh token
                Optional.ofNullable(client)
                        .map(OAuth2AuthorizedClient::getRefreshToken)
                        .map(OAuth2RefreshToken::getTokenValue)
                        .ifPresent(refreshToken -> tokenRevocationService.revokeRefreshToken(refreshToken)
                                .doOnSuccess(unused -> log.info("Refresh token revoked."))
                                .doOnError(error -> log.warn("Revocation failed: {}", error.getMessage()))
                                .subscribe());

                // 移除 client
                authorizedClientRepository.removeAuthorizedClient(clientId, authentication, request, response);
                log.info("{} 已移除", clientId);
            }

            // 登出 authorization server 並重導回 portal
            response.sendRedirect(logoutRedirectUri);
        };
    }
}
