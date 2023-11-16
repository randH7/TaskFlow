package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class ListOfTaskDTO {

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

    @NotBlank
    private Employ employ;
    public ListOfTaskDTO(String taskName, Date startDate, Date dueDate, String description, TaskStatus taskStatus, PriorityStatus priorityStatus, Employ employ) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.taskStatus = taskStatus;
        this.priorityStatus = priorityStatus;
        this.employ = employ;
    }

    @Override
    public String toString() {
        return "\nTask Name: " + taskName +
                "\nStart Date: " + startDate +
                "\nDue Date: " + dueDate +
                "\nDescription: " + description +
                "\nTask Status: " + taskStatus +
                "\nPriority Status: " + priorityStatus +
                "\nEmploy: " + employ.getUsername() + "\n";
    }
}
