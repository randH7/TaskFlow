package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import lombok.*;

import java.util.Date;

@Getter @Setter @RequiredArgsConstructor
public class ListOfTaskDTO {

    private String taskName;

    private Date startDate;

    private Date dueDate;

    private String description;

    private TaskStatus taskStatus;

    private PriorityStatus priorityStatus;

    private Employ employ;

//    public ListOfTaskDTO(String taskName, Date startDate, Date dueDate, String description, TaskStatus taskStatus, PriorityStatus priorityStatus, Employ employ) {
//        this.taskName = taskName;
//        this.startDate = startDate;
//        this.dueDate = dueDate;
//        this.description = description;
//        this.taskStatus = taskStatus;
//        this.priorityStatus = priorityStatus;
//        this.employ = employ;
//    }

    @Override
    public String toString() {
        return "\nTask Name: " + taskName +
                "\nStart Date: " + startDate +
                "\nDue Date: " + dueDate +
                "\nDescription: " + description +
                "\nTask Status: " + taskStatus +
                "\nPriority Status: " + priorityStatus +
                "\nEmploy: " + employ.getUsername() + "\n";
    }
}
