package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.ListOfTaskDTO;
import com.rand.TaskFlow.DTO.TaskDTO;
import com.rand.TaskFlow.service.implementations.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/taskflow")
public class TaskControllerImpl {

    @Autowired
    TaskServiceImpl taskService;

    @PostMapping("/projects/{projectId}/add-task")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProject(@PathVariable Integer projectId, @RequestBody @Valid TaskDTO newTask) {

        try {
            if (!taskService.isAssignToProject(newTask.getTeamMember(), projectId))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Authorize to Add New Task in This Project.");
            taskService.createTask(projectId, newTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(  "["+newTask.getTaskName()+"] Task Created Successfully. Now Let's Start");
        }catch (Exception e){
            String messageError = "Task Not Created Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;

        }

    }

    @PatchMapping("/projects/{projectId}/edit-tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> editTask(@PathVariable Integer projectId, @PathVariable Integer taskId, @RequestBody HashMap<String, Object> updatesTask){

        try {
             String message = taskService.editTask(projectId, taskId, updatesTask);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Project Not Updated Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("/my-tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getTasks() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();

        try{
            List<ListOfTaskDTO> tasks = taskService.getTasks(username);
            String listTasks = "";
            for (ListOfTaskDTO task: tasks) {
                listTasks += task.toString() + "\n";
            }
            return ResponseEntity.status(HttpStatus.OK).body("List of All Yours Tasks: \n" + listTasks);
        }catch (Exception e){
            String messageError = "Could not to listing the tasks. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }
    }

    @DeleteMapping("/projects/{projectId}/delete-tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteTask(@PathVariable Integer projectId, @PathVariable Integer taskId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getPrincipal().toString();

        try {
            String message = taskService.deleteTask(projectId, taskId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Task Not Deleted Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;
        }

    }

}
