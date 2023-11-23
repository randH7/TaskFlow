package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.*;

import java.sql.Date;

@Getter @Setter
public class ListOfProjectsDTO {

    private Integer projectId;

    private String projectName;

    private Date dueDate;

    private String manager;

    private String leader;

    private String projectStatus;

    public ListOfProjectsDTO(Integer projectId, String projectName, Date dueDate, String manager, String leader, ProjectStatus projectStatus) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.dueDate = dueDate;
        this.manager = manager;
        this.leader = leader;
        this.projectStatus = projectStatus.name();
    }

}
