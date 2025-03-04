package com.microservice.userService.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    @NotNull(message = "User name can't be null")
    @NotEmpty(message = "User name can't be empty")
    private String username;
    @NotNull(message = "Name can't be null")
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    private String password;
    @NotNull(message = "Roles can't be null")
    @NotEmpty(message = "Roles can't be empty")
    private Set<String> roles;
}
