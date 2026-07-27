package com.skillforge.identity.mapper;

import com.skillforge.identity.dto.request.CreateUserRequest;
import com.skillforge.identity.dto.request.UpdateUserRequest;
import com.skillforge.identity.dto.response.UserResponse;
import com.skillforge.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);

    // Une fonctionnalité avancée de MapStruct : Mettre à jour une entité existante
    void updateEntityFromRequest(UpdateUserRequest request, @MappingTarget User user);
}