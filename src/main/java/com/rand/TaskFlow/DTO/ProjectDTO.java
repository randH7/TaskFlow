package com.rand.TaskFlow.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter @RequiredArgsConstructor
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
