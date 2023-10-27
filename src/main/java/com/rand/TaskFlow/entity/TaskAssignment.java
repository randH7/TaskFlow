package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

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
    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(value = CONSTRAINT, foreignKeyDefinition = "FOREIGN KEY (task_id) REFERENCES tbl_task(task_id) ON DELETE CASCADE"))
    private Task task;

    public TaskAssignment(TeamMember teamMember, Task task) {
        this.teamMember = teamMember;
        this.task = task;
    }

}
