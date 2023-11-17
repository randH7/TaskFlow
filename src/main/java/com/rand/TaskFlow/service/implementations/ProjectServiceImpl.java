package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.DTO.ProjectDTO;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.entity.enums.ProjectStatus;
import com.rand.TaskFlow.repository.ManagerRepository;
import com.rand.TaskFlow.repository.ProjectAssignmentRepository;
import com.rand.TaskFlow.repository.ProjectRepository;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.service.interfaces.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployRepository employRepo;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepo;

    @Override
    public void createProject(String managerUsername, ProjectDTO newProject) {

        Manager managerSelected = managerRepo.findByUsername(managerUsername);
        Employ leaderSelected = employRepo.findByUsername(newProject.getLeaderUsername());
        Project project = new Project(newProject.getProjectName(), managerSelected, leaderSelected, newProject.getStartDate(), newProject.getDueDate(), newProject.getDescription(), ProjectStatus.IN_PROGRESS);
        projectRepo.save(project);

        for (String employUsername: newProject.getEmployeesUsername()) {
            Employ employSelected = employRepo.findByUsername(employUsername);
            ProjectAssignment projectAssignment = new ProjectAssignment(employSelected, projectRepo.findByProjectName(newProject.getProjectName()));
            projectAssignmentRepo.save(projectAssignment);
        }

    }

    @Override
    public String editProject(String mangerUsername, Integer projectId, HashMap<String, Object> updatesProject) {

        Optional<Project> projectFound = projectRepo.findById(projectId);

        if(projectFound.isPresent()){
            Project existingProject = projectFound.get();
            List<ProjectAssignment> existingProjectAssignments = projectAssignmentRepo.findByProject(existingProject);
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
                        entitiesToDelete = existingProjectAssignments.stream()
                                .filter(projectAssignment -> !employeesUsername.contains(projectAssignment.getEmploy().getUsername()))
                                .collect(Collectors.toList());

                        // Identify new employees to add
                        List<String> newEmployees = employeesUsername.stream()
                                .filter(username -> existingProjectAssignments.stream()
                                        .noneMatch(pa -> pa.getEmploy().getUsername().equals(username)))
                                .collect(Collectors.toList());

                        // Create new ProjectAssignment entities for new employees
                        newProjectAssignments = newEmployees.stream()
                                .map(username -> {
                                    return new ProjectAssignment(employRepo.findByUsername(username), existingProject);
                                })
                                .collect(Collectors.toList());
                        break;
                    case "description":
                        existingProject.setDescription((String) fieldValue);
                        break;
                    case "startDate":
                        String startDate = fieldValue.toString();
                        existingProject.setStartDate(java.sql.Date.valueOf(startDate));
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
                projectAssignmentRepo.deleteAll(entitiesToDelete);
                projectAssignmentRepo.saveAll(newProjectAssignments);
            }

            projectRepo.save(existingProject);
            return  "["+existingProject.getProjectName()+"] Project Updated Successfully.";
        }
        return "Project Not Found.";
    }

    @Override
    public List<ListOfProjectsDTO> getProjects(String username, String typeRole) {

        if(typeRole.equals("[ROLE_MANAGER]"))
            return projectRepo.findByUsernameForManager(username);
        else
            return projectRepo.findByUsernameForEmploy(username);
    }

    @Override
    public String deleteProject(Integer projectId) {

        Optional<Project> projectFound = projectRepo.findById(Integer.valueOf(projectId));

        if(projectFound.isPresent()){
            projectRepo.deleteById(projectId);
            return  "["+projectFound.get().getProjectName()+"] Project Deleted Successfully.";
        }

        return "Project Not Found.";
    }

}
