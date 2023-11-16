package com.rand.TaskFlow.DTO;

import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import lombok.*;

import java.util.Date;

@Getter @Setter @RequiredArgsConstructor
public class TaskDTO {

    private String taskName;

    private Date startDate;

    private Date dueDate;

    private String description;

    private TaskStatus taskStatus;

    private PriorityStatus priorityStatus;

    private String teamMember;

}
