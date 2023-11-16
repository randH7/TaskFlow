package com.rand.TaskFlow.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.Manager;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class ListOfProjectsDTO {

    @NotBlank
    private String projectName;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;

    @NotBlank
    private Manager manager;

    @NotBlank
    private Employ leader;

    @NotNull
    private ProjectStatus projectStatus;

    public ListOfProjectsDTO(String projectName, Date startDate, Date dueDate, Manager manager, Employ leader, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.manager = manager;
        this.leader = leader;
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "\nProject Name: " + projectName +
                "\nStart Date: " + startDate +
                "\nDue Date: " + dueDate +
                "\nManager: " + manager.getUsername() +
                "\nLeader: " + leader.getUsername() +
                "\nProject Status: " + projectStatus + "\n";
    }
}
