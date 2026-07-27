package com.skillforge.learning.client;

import com.skillforge.learning.dto.UserDto;
import com.skillforge.learning.exception.IdentityServiceUnavailableException;
import com.skillforge.learning.exception.UserNotFoundException;

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
            .uri(identityServiceUrl + "/{id}", id)
            .retrieve()

            .onStatus(
                    status -> status.value() == 404,
                    (request, response) -> {
                        throw new UserNotFoundException("User not found with id: " + id);
                    })

            .onStatus(
                    status -> status.is5xxServerError(),
                    (request, response) -> {
                        throw new IdentityServiceUnavailableException("Identity service is unavailable");
                    })

            .body(UserDto.class);
}
}