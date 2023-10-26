package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DOT.ListOfTaskDOT;
import com.rand.TaskFlow.DOT.TaskDOT;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface TaskService {

    void createTask(Integer projectId, TaskDOT newTask);

    String editTask(Integer projectId, Integer taskId, HashMap<String, Object> updatesTask) throws ParseException;

    List<ListOfTaskDOT> getTasks(String username);

    String deleteTask(Integer projectId, Integer taskId);

    boolean isAssignToProject(String teamMember, Integer projectId);

}
