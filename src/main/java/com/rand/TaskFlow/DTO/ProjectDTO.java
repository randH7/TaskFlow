package com.rand.TaskFlow.DTO;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class ProjectDTO {

    private String projectName;

    private String leaderUsername;

    private Date startDate;

    private Date dueDate;

    private String description;

    private String projectStatus;

    private List<String> employeesUsername;

    public ProjectDTO(String projectName, String leaderUsername, Date startDate, Date dueDate, String description, String projectStatus, List<String> employeesUsername) {
        this.projectName = projectName;
        this.leaderUsername = leaderUsername;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus;
        this.employeesUsername = employeesUsername;
    }

}
