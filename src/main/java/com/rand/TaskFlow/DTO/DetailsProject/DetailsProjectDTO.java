package com.rand.TaskFlow.DTO.DetailsProject;

import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
public class DetailsProjectDTO {

    private String projectName;
    private String manager;
    private String leader;
    private Date startDate;
    private Date dueDate;
    private String description;
    private String projectStatus;

    public DetailsProjectDTO(String projectName, Date startDate, Date dueDate, String manager, String leader, ProjectStatus projectStatus, String description) {
        this.projectName = projectName;
        this.manager = manager;
        this.leader = leader;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus.name();
    }

}
