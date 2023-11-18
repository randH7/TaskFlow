package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskEmployeesDTO;
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
@RequestMapping("/api/tasks")
public class TaskControllerImpl {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping("/add-task")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskDTO newTask) {

        try {
            taskService.createTask(newTask);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task Created Successfully. Now Let's Start");
        }catch (Exception e){
            String messageError = "Task Not Created Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;

        }

    }

    @PatchMapping("/edit-tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> editTask(@PathVariable Integer taskId, @RequestBody HashMap<String, Object> updatesTask){

        try {
             String message = taskService.editTask(taskId, updatesTask);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Project Not Updated Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTasks() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try{
            List<ListOfTaskDTO> tasks = taskService.getTasks(username);
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        }catch (Exception e){
            String messageError = "Could not to listing the tasks. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTasksDetails(@PathVariable Integer taskId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try{
            DetailsTaskEmployeesDTO detailsTasks = taskService.getTaskDetails(taskId, username);
            return ResponseEntity.status(HttpStatus.OK).body(detailsTasks);
        }catch (Exception e){
            String messageError = "Could not to listing the tasks. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }
    }

    @DeleteMapping("/delete-tasks/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId){

        try {
            String message = taskService.deleteTask(taskId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Task Not Deleted Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;
        }

    }

}
