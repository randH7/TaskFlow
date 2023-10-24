package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

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

    private boolean active;

    @ManyToMany(fetch = EAGER)
    private Set<Role> roles;

}
