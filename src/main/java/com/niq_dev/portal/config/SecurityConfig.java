package com.niq_dev.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

import com.niq_dev.portal.service.OAuthClientService;
import com.niq_dev.portal.service.TokenRevocationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {
	
    @Value("${oauth2.logout-redirect-uri}")
    private String logoutRedirectUri;
    
    private static final String CLIENT_ID = "portal-client";
    
    @Autowired
    private TokenRevocationService tokenRevocationService;
    
    @Autowired
    private OAuthClientService oAuthClientService;

    @Bean
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http,
    	    OAuth2AuthorizedClientRepository authorizedClientRepository, 
    	    ClientRegistrationRepository clientRegistrationRepository, 
    	    OAuth2AuthorizedClientService authorizedClientService) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/resources/**", "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
        		.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessHandler((request, response, authentication) -> {
                	if (authentication instanceof OAuth2AuthenticationToken) {
                        // 作廢 refresh token
                        OAuth2AuthorizedClient client = oAuthClientService.getAuthorizedClient((OAuth2AuthenticationToken) authentication, CLIENT_ID);
                        if (client != null && client.getRefreshToken() != null) {
                            String refreshToken = client.getRefreshToken().getTokenValue();
                            tokenRevocationService.revokeRefreshToken(refreshToken)
											.doOnSuccess(unused -> log.info("Refresh token revoked."))
											.doOnError(error -> log.warn("Revocation failed: {}", error.getMessage()))
											.subscribe();
                        }
                	}

                	// 移除已授權 client (從 session 或 cookie)
                    if (authentication != null) {
                        authorizedClientRepository.removeAuthorizedClient(CLIENT_ID, authentication, request, response);
                        log.info(CLIENT_ID + " 已移除");
                    }
                    
                    // 導向 logout URL
                    response.sendRedirect(logoutRedirectUri);
                })
    		)
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true)); // 啟用 OAuth2 login
        return http.build();
    }
    
}
