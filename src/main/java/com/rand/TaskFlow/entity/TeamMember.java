package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_team_member")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamMember extends User {

    @ManyToMany
    @JoinTable(
            name = "tbl_team_member_projects",
            joinColumns = { @JoinColumn(name = "username")},
            inverseJoinColumns = { @JoinColumn(name = "project_id")}
    )
    private List<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "tbl_team_member_tasks",
            joinColumns = { @JoinColumn(name = "username")},
            inverseJoinColumns = { @JoinColumn(name = "task_id")}
    )
    private List<Task> tasks;

}
