package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.AddProjectDTO;
import com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectEmployeesDTO;
import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
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
@RequestMapping("/api/projects")
public class ProjectControllerImpl {

    @Autowired
    private ProjectServiceImpl projectService;

    @PostMapping("/create-project")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProject(@RequestBody @Valid AddProjectDTO newProject){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String managerUsername = auth.getName();

        try {
            projectService.createProject(managerUsername, newProject);
            return ResponseEntity.status(HttpStatus.CREATED).body("Project Created Successfully. Now Let's Start");
        }catch (Exception e){
            String messageError = "Project Not Created Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;

        }

    }

    @PatchMapping("/edit-project/{projectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> editProject(@PathVariable Integer projectId,@RequestBody HashMap<String, Object> updatesProject){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getName();

        try {
            String message = projectService.editProject(mangerUsername, projectId, updatesProject);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Project Not Updated Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;

        }

    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProjects() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String typeRole = auth.getAuthorities().toString();

        try{
            List<ListOfProjectsDTO> projects = projectService.getProjects(username, typeRole);
            return ResponseEntity.status(HttpStatus.OK).body(projects);
        }catch (Exception e){
            String messageError = "Can not to listing the projects. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProjectDetails(@PathVariable Integer projectId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String typeRole = auth.getAuthorities().toString();

        try{
            DetailsProjectEmployeesDTO detailsProject = projectService.getProjectDetails(username, typeRole, projectId);
            return ResponseEntity.status(HttpStatus.OK).body(detailsProject);
        }catch (Exception e){
            String messageError = "Can not to listing the projects. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @DeleteMapping("/delete-project/{projectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId){

        try {
            String message = projectService.deleteProject(projectId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Project Not Deleted Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;
        }

    }

}
