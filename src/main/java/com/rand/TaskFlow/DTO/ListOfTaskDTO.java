package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import lombok.*;

import java.util.Date;

@Getter @Setter
public class ListOfTaskDTO {

    private String taskName;
    private Date dueDate;
    private String taskStatus;
    private String priorityStatus;

    public ListOfTaskDTO(String taskName, Date dueDate, TaskStatus taskStatus, PriorityStatus priorityStatus) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus.name();
        this.priorityStatus = priorityStatus.name();
    }

}
