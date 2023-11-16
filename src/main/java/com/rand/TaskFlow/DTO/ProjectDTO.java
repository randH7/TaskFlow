package com.rand.TaskFlow.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class ProjectDTO {

    private String projectName;

    private String leaderUsername;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String description;

    private ProjectStatus projectStatus;

    private List<String> teamMembersUsername;

    public ProjectDTO(String projectName, String leaderUsername, Date startDate, Date dueDate, String description, ProjectStatus projectStatus, List<String> teamMembersUsername) {
        this.projectName = projectName;
        this.leaderUsername = leaderUsername;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus;
        this.teamMembersUsername = teamMembersUsername;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "projectName='" + projectName + '\'' +
                ", leaderUsername='" + leaderUsername + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", projectStatus=" + projectStatus +
                ", teamMembersUsername=" + teamMembersUsername +
                '}';
    }

}
