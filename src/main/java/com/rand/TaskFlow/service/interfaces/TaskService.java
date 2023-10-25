package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DOT.TaskDOT;

public interface TaskService {

    void createTask(Integer projectId, TaskDOT newTask);

    boolean isAssignToProject(String teamMember, Integer projectId);
}
