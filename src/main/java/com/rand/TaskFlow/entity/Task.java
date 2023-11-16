package com.rand.TaskFlow.entity;

import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tbl_task")
@Getter @Setter @NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @NotBlank
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PriorityStatus priorityStatus;

    public Task(String taskName, Project project, Date startDate, Date dueDate, String description, TaskStatus taskStatus, PriorityStatus priorityStatus) {
        this.taskName = taskName;
        this.project = project;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.taskStatus = taskStatus;
        this.priorityStatus = priorityStatus;
    }

}
