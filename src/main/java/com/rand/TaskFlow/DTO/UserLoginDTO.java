package com.rand.TaskFlow.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserLoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
