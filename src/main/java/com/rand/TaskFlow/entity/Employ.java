package com.rand.TaskFlow.entity;

import com.rand.TaskFlow.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_employ")
@Getter @Setter @NoArgsConstructor
public class Employ extends User {

    @ManyToOne
    @JoinColumn(name = "manager")
    private Manager manager;

    @ManyToMany
    @JoinTable(
            name = "tbl_project_assignment",
            joinColumns = { @JoinColumn(name = "username")},
            inverseJoinColumns = { @JoinColumn(name = "project_id")}
    )
    private List<ProjectAssignment> projectAssignments;

    @ManyToMany
    @JoinTable(
            name = "tbl_task_assignment",
            joinColumns = { @JoinColumn(name = "username")},
            inverseJoinColumns = { @JoinColumn(name = "task_id")}
    )
    private List<TaskAssignment> taskAssignments;

    public Employ(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password, @NotBlank String employName, @NotBlank String jobTitle, Role role) {
        super(username, email, password, employName, jobTitle, role);
    }

}
