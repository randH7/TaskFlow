package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class TaskDTO {

    private Integer projectId;

    private String taskName;

    private Date startDate;

    private Date dueDate;

    private String description;

    private TaskStatus taskStatus;

    private PriorityStatus priorityStatus;

    private List<String> employeesUsername;

    public TaskDTO(Integer projectId, String taskName, Date startDate, Date dueDate, String description, TaskStatus taskStatus, PriorityStatus priorityStatus, List<String> employeesUsername) {
        this.projectId = projectId;
        this.taskName = taskName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.taskStatus = taskStatus;
        this.priorityStatus = priorityStatus;
        this.employeesUsername = employeesUsername;
    }

}
