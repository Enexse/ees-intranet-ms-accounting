package com.enexse.intranet.ms.accounting.configurations;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersFeignClientConfig {
    @Autowired
    private KeycloakProvider tokenProvider;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = tokenProvider.getAccessToken(); // Get the user's access token
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
