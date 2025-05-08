package com.niq_dev.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 這裡是 Redirect 登出到授權伺服器的 logout URL
                    response.sendRedirect("http://localhost:8081/oidc/logout?post_logout_redirect_uri=http://localhost:8080/");
                })
    		)
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/", true)); // 啟用 OAuth2 login
        return http.build();
    }
}
