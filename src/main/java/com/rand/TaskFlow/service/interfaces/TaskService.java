package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.ListOfTaskDTO;
import com.rand.TaskFlow.DTO.TaskDTO;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface TaskService {

    void createTask(TaskDTO newTask);

    String editTask(Integer projectId, Integer taskId, HashMap<String, Object> updatesTask) throws ParseException;

    List<ListOfTaskDTO> getTasks(String username);

    String deleteTask(Integer projectId, Integer taskId);

    boolean isAssignToProject(String teamMember, Integer projectId);

}
