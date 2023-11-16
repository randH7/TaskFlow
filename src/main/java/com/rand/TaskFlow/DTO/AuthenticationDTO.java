package com.rand.TaskFlow.DTO;

import lombok.*;

@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthenticationDTO {

    private String access_token;

}
