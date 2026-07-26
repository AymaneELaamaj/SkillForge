package com.skillforge.learning.service;

import com.skillforge.learning.client.IdentityServiceClient;
import com.skillforge.learning.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class LearningService {

    private final IdentityServiceClient identityClient;

    public LearningService(IdentityServiceClient identityClient) {
        this.identityClient = identityClient;
    }

    public UserDto getUserInfoFromIdentity(Long id) {
        try {
        return identityClient.getUserById(id);
        }
        catch(Exception e){
            throw new RuntimeException("Identity Service unavailable");
        }
    }
}