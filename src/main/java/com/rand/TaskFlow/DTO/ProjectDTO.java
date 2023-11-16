package com.rand.TaskFlow.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ProjectDTO {

    @NotBlank
    private String projectName;

    @NotBlank
    private String leaderUsername;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @NotBlank
    private String description;

    @NotNull
    private ProjectStatus projectStatus;

    @NotNull
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
