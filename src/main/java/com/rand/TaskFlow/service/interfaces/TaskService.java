package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskEmployeesDTO;
import com.rand.TaskFlow.DTO.ListOfTaskDTO;
import com.rand.TaskFlow.DTO.TaskDTO;

import java.util.HashMap;
import java.util.List;

public interface TaskService {

    void createTask(TaskDTO newTask);

    String editTask(Integer taskId, HashMap<String, Object> updatesTask);

    List<ListOfTaskDTO> getTasks(String username);

    DetailsTaskEmployeesDTO getTaskDetails(Integer taskId, String username);

    String deleteTask(Integer taskId);

}
