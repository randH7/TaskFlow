package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskDTO;
import com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskEmployeesDTO;
import com.rand.TaskFlow.DTO.ListOfTaskDTO;
import com.rand.TaskFlow.DTO.TaskDTO;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.entity.enums.PriorityStatus;
import com.rand.TaskFlow.entity.enums.TaskStatus;
import com.rand.TaskFlow.repository.*;
import com.rand.TaskFlow.service.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private TaskAssignmentServiceImpl taskAssignmentService;

    @Override
    public void createTask(TaskDTO newTask) {

        Task task = new Task(newTask.getTaskName(), projectRepo.findByProjectId(newTask.getProjectId()), newTask.getStartDate(), newTask.getDueDate(), newTask.getDescription(), newTask.getTaskStatus(), newTask.getPriorityStatus());
        taskRepo.save(task);

        taskAssignmentService.assignTaskToEmployees(newTask.getEmployeesUsername(), task);
    }

    @Override
    public String editTask(Integer taskId, HashMap<String, Object> updatesTask) {

        Optional<Task> taskFound = taskRepo.findById(taskId);

        if(taskFound.isPresent()) {
            Task existingTask = taskFound.get();
            List<TaskAssignment> newTaskAssignments = new ArrayList<>();
            List<TaskAssignment> entitiesToDelete = new ArrayList<>();

            for (HashMap.Entry<String, Object> entry : updatesTask.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                switch (fieldName) {
                    case "taskName":
                        existingTask.setTaskName((String) fieldValue);
                        break;
                    case "dueDate":
                        String dueDate = fieldValue.toString();
                        existingTask.setDueDate(java.sql.Date.valueOf(dueDate));
                        break;
                    case "description":
                        existingTask.setDescription((String) fieldValue);
                        break;
                    case "taskStatus":
                        existingTask.setTaskStatus(TaskStatus.valueOf((String)fieldValue));
                        break;
                    case "priorityStatus":
                        existingTask.setPriorityStatus(PriorityStatus.valueOf((String)fieldValue));
                        break;
                    case "employeesUsername":
                        List<String> employeesUsername = (List<String>)fieldValue;
                        // Identify entities to delete
                        entitiesToDelete = taskAssignmentService.IdentifyEntitiesDelete(employeesUsername, existingTask);
                        // Identify new employees to assign them in the task (new TaskAssignment entities)
                        newTaskAssignments = taskAssignmentService.IdentifyTaskAssignmentEmployees(employeesUsername, existingTask);
                        break;
                }
            }

            if(!newTaskAssignments.isEmpty() || !entitiesToDelete.isEmpty()) {
                taskAssignmentService.editAssignTaskToEmployees(entitiesToDelete, newTaskAssignments);
            }

            taskRepo.save(existingTask);
            return  "Task Updated Successfully.";

        }
        return "Task Not Found.";

    }

    @Override
    public List<ListOfTaskDTO> getTasks(String username) {
        return taskRepo.findByUsername(username);
    }

    @Override
    public DetailsTaskEmployeesDTO getTaskDetails(Integer taskId, String username) {

        DetailsTaskDTO detailsTask = taskRepo.findDetailsById(taskId, username);
        List<String> employeesUsername = taskAssignmentService.findTaskOrderByByEmploy(taskId);

        return new DetailsTaskEmployeesDTO(detailsTask, employeesUsername);

    }

    @Override
    public String deleteTask(Integer taskId) {

        taskRepo.deleteById(taskId);
        return  "Task Deleted Successfully.";

    }

}
