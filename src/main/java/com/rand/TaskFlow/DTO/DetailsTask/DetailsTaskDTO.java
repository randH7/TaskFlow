package com.rand.TaskFlow.DTO.DetailsTask;

import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class DetailsTaskDTO {

    private String taskName;
    private String projectName;
    private Date startDate;
    private Date dueDate;
    private String taskStatus;
    private String priorityStatus;
    private String description;

    public DetailsTaskDTO(String taskName, String projectName, Date startDate, Date dueDate, TaskStatus taskStatus, PriorityStatus priorityStatus, String description) {
        this.taskName = taskName;
        this.projectName = projectName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus.name();
        this.priorityStatus = priorityStatus.name();
        this.description = description;
    }

}
