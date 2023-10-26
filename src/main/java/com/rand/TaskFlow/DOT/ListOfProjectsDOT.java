package com.rand.TaskFlow.DOT;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rand.TaskFlow.entity.Manger;
import com.rand.TaskFlow.entity.ProjectStatus;
import com.rand.TaskFlow.entity.TeamMember;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ListOfProjectsDOT {

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
    private Manger manger;

    @NotBlank
    private TeamMember leader;

    @NotNull
    private ProjectStatus projectStatus;

    public ListOfProjectsDOT(String projectName, Date startDate, Date dueDate, Manger manger, TeamMember leader, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.manger = manger;
        this.leader = leader;
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "\nProject Name: " + projectName +
                "\nStart Date: " + startDate +
                "\nDue Date: " + dueDate +
                "\nManger: " + manger.getUsername() +
                "\nLeader: " + leader.getUsername() +
                "\nProject Status: " + projectStatus + "\n";
    }
}
