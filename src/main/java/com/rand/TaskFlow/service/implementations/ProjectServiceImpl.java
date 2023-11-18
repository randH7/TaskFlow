package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DTO.AddProjectDTO;
import com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectDTO;
import com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectEmployeesDTO;
import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import com.rand.TaskFlow.repository.ManagerRepository;
import com.rand.TaskFlow.repository.ProjectRepository;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployRepository employRepo;

    @Autowired
    private ProjectAssignmentServiceImpl projectAssignmentService;

    @Override
    public void createProject(String managerUsername, AddProjectDTO newProject) {

        Manager managerSelected = managerRepo.findByUsername(managerUsername);
        Employ leaderSelected = employRepo.findByUsername(newProject.getLeaderUsername());
        Project project = new Project(newProject.getProjectName(), managerSelected, leaderSelected, newProject.getStartDate(), newProject.getDueDate(), newProject.getDescription(), ProjectStatus.IN_PROGRESS);
        projectRepo.save(project);

        projectAssignmentService.assignProjectToEmployees(newProject.getEmployeesUsername(), projectRepo.findByProjectName(newProject.getProjectName()));

    }

    @Override
    public String editProject(String mangerUsername, Integer projectId, HashMap<String, Object> updatesProject) {

        Optional<Project> projectFound = projectRepo.findById(projectId);

        if(projectFound.isPresent()){
            Project existingProject = projectFound.get();
            List<ProjectAssignment> newProjectAssignments = new ArrayList<>();
            List<ProjectAssignment> entitiesToDelete = new ArrayList<>();

            for (HashMap.Entry<String, Object> entry : updatesProject.entrySet()){
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                switch (fieldName) {
                    case "projectName":
                        existingProject.setProjectName((String) fieldValue);
                        break;
                    case "leaderUsername":
                        Employ leaderSelected = employRepo.findByUsername((String) fieldValue);
                        existingProject.setLeader(leaderSelected);
                        break;
                    case "employeesUsername":
                        List<String> employeesUsername = (List<String>)fieldValue;
                        // Identify entities to delete
                        entitiesToDelete = projectAssignmentService.IdentifyEntitiesDelete(employeesUsername, existingProject);
                        // Identify new employees to assign them in the project (new ProjectAssignment entities)
                        newProjectAssignments = projectAssignmentService.IdentifyProjectAssignmentEmployees(employeesUsername, existingProject);
                        break;
                    case "description":
                        existingProject.setDescription((String) fieldValue);
                        break;
                    case "dueDate":
                        String dueDate = fieldValue.toString();
                        existingProject.setDueDate(java.sql.Date.valueOf(dueDate));
                        break;
                    case "projectStatus":
                        existingProject.setProjectStatus(ProjectStatus.valueOf((String)fieldValue));
                        break;
                }
            }

            if(!newProjectAssignments.isEmpty() || !entitiesToDelete.isEmpty()) {
                projectAssignmentService.editAssignProjectToEmployees(entitiesToDelete, newProjectAssignments);
            }

            projectRepo.save(existingProject);
            return  "["+existingProject.getProjectName()+"] Project Updated Successfully.";
        }
        return "Project Not Found.";
    }

    @Override
    public List<ListOfProjectsDTO> getProjects(String username, String typeRole) {

        if(typeRole.equals("[ROLE_MANAGER]"))
            return projectRepo.findProjectsByUsernameForManager(username);
        else
            return projectRepo.findProjectsByUsernameForEmploy(username);
    }

    @Override
    public DetailsProjectEmployeesDTO getProjectDetails(String username, String typeRole, Integer projectId) {

        DetailsProjectDTO detailsProject;
        List<String> employeesUsername = projectAssignmentService.findByProjectOrderByEmploy(projectId);

        if(typeRole.equals("[ROLE_MANAGER]")) {
            detailsProject = projectRepo.findDetailsByIdForManager(projectId, username);
        }
        else {
            detailsProject = projectRepo.findDetailsByIdForEmploy(projectId, username);
        }

        return new DetailsProjectEmployeesDTO(detailsProject, employeesUsername);

    }

    @Override
    public String deleteProject(Integer projectId) {

        Optional<Project> projectFound = projectRepo.findById(projectId);
        projectRepo.deleteById(projectId);
        return  "["+projectFound.get().getProjectName()+"] Project Deleted Successfully.";

    }

}
