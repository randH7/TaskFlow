package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DOT.TaskDOT;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.repository.*;
import com.rand.TaskFlow.service.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    ProjectRepository projectRepo;

    @Autowired
    ProjectAssignmentRepository projectAssignmentRepo;

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    TaskAssignmentRepository taskAssignmentRepo;

    @Autowired
    TeamMemberRepository teamMemberRepo;

    @Override
    public void createTask(Integer projectId, TaskDOT newTask) {

        Task task = new Task(newTask.getTaskName(), projectRepo.findByProjectId(projectId), newTask.getStartDate(), newTask.getDueDate(), newTask.getDescription(), newTask.getTaskStatus(), newTask.getPriorityStatus());
        taskRepo.save(task);

        TaskAssignment taskAssignment = new TaskAssignment(teamMemberRepo.findByUsername(newTask.getTeamMember()), task);
        taskAssignmentRepo.save(taskAssignment);

    }

    @Override
    public boolean isAssignToProject(String teamMember, Integer projectId) {

        if(projectAssignmentRepo.findByTeamMemberAndProject(teamMemberRepo.findByUsername(teamMember), projectRepo.findByProjectId(projectId)).isPresent())
            return true;
        return false;

    }

}
