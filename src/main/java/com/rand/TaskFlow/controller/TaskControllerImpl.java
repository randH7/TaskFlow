package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.DOT.ListOfTaskDOT;
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

import java.util.List;

@RestController
@RequestMapping("/taskflow")
public class TaskControllerImpl {

    @Autowired
    TaskServiceImpl taskService;

    @PostMapping("/projects/{projectId}/add-task")
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

    @GetMapping("/my-tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getTasks() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();

        try{
            List<ListOfTaskDOT> tasks = taskService.getTasks(username);
            String listTasks = "";
            for (ListOfTaskDOT task: tasks) {
                listTasks += task.toString() + "\n";
            }
            return ResponseEntity.status(HttpStatus.OK).body("List of All Yours Tasks: \n" + listTasks);
        }catch (Exception e){
            String messageError = "Could not to listing the tasks. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }
    }

}
