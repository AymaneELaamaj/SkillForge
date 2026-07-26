package com.skillforge.learning.client;

import com.skillforge.learning.dto.UserDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IdentityServiceClient {

    private final RestClient restClient;
    // URL codée en dur pour le MVP.
    @Value("${identity-service.url}")
    private String identityServiceUrl;

    public IdentityServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public UserDto getUserById(Long id) {
        return restClient.get()
                .uri(identityServiceUrl + "/users/{id}", id)
                .retrieve()
                .body(UserDto.class); // Spring désérialise le JSON directement dans ton Record !
    }
}