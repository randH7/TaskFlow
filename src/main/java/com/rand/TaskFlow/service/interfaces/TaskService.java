package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DOT.ListOfTaskDOT;
import com.rand.TaskFlow.DOT.TaskDOT;

import java.util.List;

public interface TaskService {

    void createTask(Integer projectId, TaskDOT newTask);

    List<ListOfTaskDOT> getTasks(String username);

    boolean isAssignToProject(String teamMember, Integer projectId);

}
