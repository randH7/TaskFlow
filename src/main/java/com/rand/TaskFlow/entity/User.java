package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tbl_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String employName;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private boolean active;

    public User(String username, String email, String password, String employName, String jobTitle) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setEmployName(employName);
        setJobTitle(jobTitle);
        this.active = true;
    }

}
