package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserVerifyDTO {

    private String username;

    private String email;

    private String employName;

    private String jobTitle;

    private Role role;

}
