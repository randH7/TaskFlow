package com.rand.TaskFlow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_project_assignment")
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

}
