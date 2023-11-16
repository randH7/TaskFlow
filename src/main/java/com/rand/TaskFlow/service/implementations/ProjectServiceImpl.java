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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        ProjectAssignment projectAssignment = new ProjectAssignment(leaderSelected, projectRepo.findByProjectName(newProject.getProjectName()));
        projectAssignmentRepo.save(projectAssignment);

        for (String employUsername: newProject.getTeamMembersUsername()) {
            Employ employSelected = employRepo.findByUsername(employUsername);
            projectAssignment = new ProjectAssignment(employSelected, projectRepo.findByProjectName(newProject.getProjectName()));
            projectAssignmentRepo.save(projectAssignment);
        }

    }

    @Override
    public String editProject(String mangerUsername, String projectId, HashMap<String, Object> updatesProject) throws ParseException {

        Optional<Project> projectFound = projectRepo.findById(Integer.valueOf(projectId));

        if(projectFound.isPresent()){
            Project existingProject = projectFound.get();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                        if (projectAssignmentRepo.findByEmployAndProject(leaderSelected, existingProject).isEmpty()){
                            ProjectAssignment projectAssignment = new ProjectAssignment(leaderSelected, existingProject);
                            projectAssignmentRepo.save(projectAssignment);
                        }
                        break;
                    case "teamMembersUsername":
                        for (String teamMemberUsername: (List<String>)fieldValue) {
                            Employ employSelected = employRepo.findByUsername(teamMemberUsername);
                            if (projectAssignmentRepo.findByEmployAndProject(employSelected, existingProject).isEmpty()) {
                                ProjectAssignment projectAssignment = new ProjectAssignment(employSelected, existingProject);
                                projectAssignmentRepo.save(projectAssignment);
                            }
                        }
                        break;
                    case "description":
                        existingProject.setDescription((String) fieldValue);
                        break;
                    case "dueDate":
                        existingProject.setDueDate(new Date(dateFormat.parse((String) fieldValue).getTime()));
                        break;
                    case "projectStatus":
                        existingProject.setProjectStatus(ProjectStatus.valueOf((String)fieldValue));
                        break;
                }
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
    public String deleteProject(String projectId) {

        Optional<Project> projectFound = projectRepo.findById(Integer.valueOf(projectId));

        if(projectFound.isPresent()){
            projectRepo.deleteById(Integer.valueOf(projectId));
            return  "["+projectFound.get().getProjectName()+"] Project Deleted Successfully.";

        }
        return "Project Not Found.";
    }

    @Override
    public boolean isMangerForProject(String mangerUsername, String projectId) {

        if(projectRepo.findByManagerAndProjectId(managerRepo.findByUsername(mangerUsername), Integer.valueOf(projectId)).isPresent()){
            return true;
        }
        return false;
    }

}
