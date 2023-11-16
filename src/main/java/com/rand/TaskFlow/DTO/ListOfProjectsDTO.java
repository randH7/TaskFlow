package com.rand.TaskFlow.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Manager;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.*;

import java.util.Date;

@Getter @Setter
public class ListOfProjectsDTO {

    private String projectName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;

    private Manager manager;

    private Employ leader;

    private ProjectStatus projectStatus;

    public ListOfProjectsDTO(String projectName, Date startDate, Date dueDate, Manager manager, Employ leader, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.manager = manager;
        this.leader = leader;
        this.projectStatus = projectStatus;
    }

}
