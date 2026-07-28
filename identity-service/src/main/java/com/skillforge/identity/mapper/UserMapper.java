package com.skillforge.identity.mapper;

import com.skillforge.identity.dto.request.RegisterRequest;
import com.skillforge.identity.dto.request.UpdateUserRequest;
import com.skillforge.identity.dto.response.UserResponse;
import com.skillforge.identity.entity.Role;
import com.skillforge.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromRequest(UpdateUserRequest request,
                                 @MappingTarget User user);

    @Named("mapRoles")
    default Set<String> mapRoles(Set<Role> roles) {

        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }

        return roles.stream()
                .map(Role::getName)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}