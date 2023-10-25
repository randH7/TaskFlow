package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.DOT.ProjectDOT;
import com.rand.TaskFlow.service.implementations.ProjectServiceImpl;
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
public class ProjectControllerImpl {

    @Autowired
    ProjectServiceImpl projectService;

    @PostMapping("/projects/create-project")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProject(@RequestBody @Valid ProjectDOT newProject){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getPrincipal().toString();

        try {
            projectService.createProject(mangerUsername, newProject);
            return ResponseEntity.status(HttpStatus.CREATED).body(  "["+newProject.getProjectName()+"] Project Created Successfully. Now Let's Start");
        }catch (Exception e){
            String messageError = "Project Not Created Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;

        }

    }

    @PatchMapping("/projects/edit-project/{projectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> editProject(@PathVariable String projectId,@RequestBody HashMap<String, Object> updatesProject){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getPrincipal().toString();

        try {
            String message = projectService.editProject(mangerUsername, projectId, updatesProject);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }catch (Exception e){
            String messageError = "Project Not Updated Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;

        }

    }


    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getProjects() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        String typeRole = auth.getAuthorities().toString();

        try{
            List<ListOfProjectsDOT> projects = projectService.getProjects(username, typeRole);
            String listProjects = "";
            for (ListOfProjectsDOT project: projects) {
                listProjects += project.toString() + "\n";
            }
            return ResponseEntity.status(HttpStatus.OK).body("List of All projects: \n" + listProjects);
        }catch (Exception e){
            String messageError = "Could not to listing the projects. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }


}
