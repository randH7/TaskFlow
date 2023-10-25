package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DOT.ProjectDOT;
import com.rand.TaskFlow.DOT.TaskDOT;
import com.rand.TaskFlow.service.implementations.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskflow/projects")
public class TaskControllerImpl {

    @Autowired
    TaskServiceImpl taskService;

    @PostMapping("/{projectId}/add-task")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProject(@PathVariable Integer projectId, @RequestBody @Valid TaskDOT newTask) {

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

}
