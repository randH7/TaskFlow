package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
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
    private Employ employ;

    @ManyToOne
    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(value = CONSTRAINT, foreignKeyDefinition = "FOREIGN KEY (task_id) REFERENCES tbl_task(task_id) ON DELETE CASCADE"))
    private Task task;

    public TaskAssignment(Employ employ, Task task) {
        this.employ = employ;
        this.task = task;
    }

}
