package com.rand.TaskFlow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_task_assignment")
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskAssignmentId;

    @ManyToOne
    @JoinColumn(name = "username")
    private TeamMember teamMember;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
