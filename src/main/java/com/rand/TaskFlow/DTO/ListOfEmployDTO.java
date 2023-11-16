package com.rand.TaskFlow.DTO;

import lombok.*;

@Getter @Setter
public class ListOfEmployDTO {

    private String username;

    private String email;

    private String employName;

    private String jobTitle;

    public ListOfEmployDTO(String username, String email, String employName, String jobTitle) {
        this.username = username;
        this.email = email;
        this.employName = employName;
        this.jobTitle = jobTitle;
    }

}
