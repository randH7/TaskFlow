package com.rand.TaskFlow.entity;

import com.rand.TaskFlow.entity.enums.Role;
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
public class Manager extends User {

    @OneToMany(mappedBy = "manager")
    private List<Employ> employs;

    @OneToMany(mappedBy = "manager")
    private List<Project> projects;

    public Manager(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, @NotBlank String employName, @NotBlank String jobTitle, @NotBlank Role role) {
        super(username, email, password, employName, jobTitle, role);
    }

}
