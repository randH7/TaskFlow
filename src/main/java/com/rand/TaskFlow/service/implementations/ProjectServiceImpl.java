package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.DOT.ProjectDOT;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.repository.MangerRepository;
import com.rand.TaskFlow.repository.ProjectAssignmentRepository;
import com.rand.TaskFlow.repository.ProjectRepository;
import com.rand.TaskFlow.repository.TeamMemberRepository;
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
    private MangerRepository mangerRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepo;

    @Override
    public void createProject(String mangerUsername, ProjectDOT newProject) {

        Manger mangerSelect = mangerRepo.findByUsername(mangerUsername);
        TeamMember leaderSelect = teamMemberRepo.findByUsername(newProject.getLeaderUsername());
        Project project = new Project(newProject.getProjectName(), mangerSelect, leaderSelect, newProject.getStartDate(), newProject.getDueDate(), newProject.getDescription(), ProjectStatus.IN_PROGRESS);

        projectRepo.save(project);
        ProjectAssignment projectAssignment = new ProjectAssignment(leaderSelect, projectRepo.findByProjectName(newProject.getProjectName()));
        projectAssignmentRepo.save(projectAssignment);

        for (String teamMemberUsername: newProject.getTeamMembersUsername()) {
            TeamMember teamMemberSelected = teamMemberRepo.findByUsername(teamMemberUsername);
            if (projectAssignmentRepo.findByTeamMemberAndProject(teamMemberSelected, projectRepo.findByProjectName(newProject.getProjectName())).isEmpty()){
                projectAssignment = new ProjectAssignment(teamMemberSelected, projectRepo.findByProjectName(newProject.getProjectName()));
                projectAssignmentRepo.save(projectAssignment);
            }
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
                        TeamMember leaderSelected = teamMemberRepo.findByUsername((String) fieldValue);
                        existingProject.setLeader(leaderSelected);
                        if (projectAssignmentRepo.findByTeamMemberAndProject(leaderSelected, existingProject).isEmpty()){
                            ProjectAssignment projectAssignment = new ProjectAssignment(leaderSelected, existingProject);
                            projectAssignmentRepo.save(projectAssignment);
                        }
                        break;
                    case "teamMembersUsername":
                        for (String teamMemberUsername: (List<String>)fieldValue) {
                            TeamMember teamMemberSelected = teamMemberRepo.findByUsername(teamMemberUsername);
                            if (projectAssignmentRepo.findByTeamMemberAndProject(teamMemberSelected, existingProject).isEmpty()) {
                                ProjectAssignment projectAssignment = new ProjectAssignment(teamMemberSelected, existingProject);
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
    public List<ListOfProjectsDOT> getProjects(String username, String typeRole) {

        if(typeRole.equals("[ROLE_MANAGER]"))
            return projectRepo.findByUsernameForManger(username);
        else
            return projectRepo.findByUsernameForTeamMember(username);
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

        if(projectRepo.findByMangerAndProjectId(mangerRepo.findByUsername(mangerUsername), Integer.valueOf(projectId)).isPresent()){
            return true;
        }
        return false;
    }

}
