package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_task")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private Integer taskId;

    @NotBlank
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotBlank
    private Project project;

    @NotBlank
    @FutureOrPresent
    private Date startDate;

    @NotBlank
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @Enumerated
    @NotBlank
    private TaskStatus taskStatus;

    @Enumerated
    @NotBlank
    private PriorityStatus priorityStatus;

}
