package com.rand.TaskFlow.DOT;

import com.rand.TaskFlow.entity.PriorityStatus;
import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.TaskStatus;
import com.rand.TaskFlow.entity.TeamMember;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor
public class TaskDOT {

    @NotBlank
    private String taskName;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @NotNull
    private TaskStatus taskStatus;

    @NotNull
    private PriorityStatus priorityStatus;

    @NotNull
    private String teamMember;

}
