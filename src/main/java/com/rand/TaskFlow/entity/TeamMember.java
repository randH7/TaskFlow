package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_team_member")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamMember extends User {

    @NotBlank
    private boolean isLeader;

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

}
