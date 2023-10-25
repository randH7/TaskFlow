package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tbl_task_assignment")
@Getter @Setter @NoArgsConstructor
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

    public TaskAssignment(TeamMember teamMember, Task task) {
        this.teamMember = teamMember;
        this.task = task;
    }

}
