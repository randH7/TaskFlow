package com.rand.TaskFlow.DTO;

import lombok.*;

@Getter @Setter @NoArgsConstructor
public class InviteEmployDTO {

    private String username;

    public InviteEmployDTO(String username) {
        this.username = username;
    }

}
