package com.rand.TaskFlow.DOT;

import com.rand.TaskFlow.entity.PriorityStatus;
import com.rand.TaskFlow.entity.TaskStatus;
import com.rand.TaskFlow.entity.TeamMember;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class ListOfTaskDOT {

    @NotBlank
    private String taskName;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @NotNull
    private TaskStatus taskStatus;

    @NotNull
    private PriorityStatus priorityStatus;

    @NotBlank
    private TeamMember teamMember;
    public ListOfTaskDOT(String taskName, Date startDate, Date dueDate, String description, TaskStatus taskStatus, PriorityStatus priorityStatus, TeamMember teamMember) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.taskStatus = taskStatus;
        this.priorityStatus = priorityStatus;
        this.teamMember = teamMember;
    }

    @Override
    public String toString() {
        return "\nTask Name: " + taskName +
                "\nStart Date: " + startDate +
                "\nDue Date: " + dueDate +
                "\nDescription: " + description +
                "\nTask Status: " + taskStatus +
                "\nPriority Status: " + priorityStatus +
                "\nTeamMember: " + teamMember.getUsername() + "\n";
    }
}
