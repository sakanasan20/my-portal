package com.niq_dev.portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
    @Value("${oauth2.logout-redirect-uri}")
    private String logoutRedirectUri;

    @Bean
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/resources/**", "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
        		.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 這裡是 Redirect 登出到授權伺服器的 logout URL
                    response.sendRedirect(logoutRedirectUri);
                })
    		)
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true)); // 啟用 OAuth2 login
        return http.build();
    }
}
