package com.rand.TaskFlow.DTO;

import lombok.*;

@Builder @Getter @Setter
public class AuthenticationDTO {

    private String access_token;

    public AuthenticationDTO(String access_token) {
        this.access_token = access_token;
    }

}
