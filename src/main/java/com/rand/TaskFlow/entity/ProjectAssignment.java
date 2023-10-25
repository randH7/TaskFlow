package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_project_assignment")
@Getter @Setter @NoArgsConstructor
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectAssignmentId;

    @ManyToOne
    @JoinColumn(name = "username")
    private TeamMember teamMember;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectAssignment(TeamMember teamMember, Project project) {
        this.teamMember = teamMember;
        this.project = project;
    }

}
