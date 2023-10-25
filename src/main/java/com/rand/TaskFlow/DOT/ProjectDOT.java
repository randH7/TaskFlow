package com.rand.TaskFlow.DOT;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.ProjectStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ProjectDOT {

    @NotBlank
    private String projectName;

    @NotBlank
    private String leaderUsername;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;

    @NotBlank
    private String description;

    @NotNull
    private ProjectStatus projectStatus;

    @NotNull
    private List<String> teamMembersUsername;

    @Override
    public String toString() {
        return "ProjectDOT{" +
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
