package com.rand.TaskFlow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_manger")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Manger extends User {

    @OneToMany(mappedBy = "manger")
    private List<Project> projects;

    public Manger(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, @NotBlank String employName, @NotBlank String jobTitle, boolean active, Set<Role> authorities) {
        super(username, email, password, employName, jobTitle, active, authorities);
    }

}
