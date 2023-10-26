package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DOT.ListOfTaskDOT;
import com.rand.TaskFlow.DOT.TaskDOT;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.repository.*;
import com.rand.TaskFlow.service.interfaces.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    public String editTask(Integer projectId, Integer taskId, HashMap<String, Object> updatesTask) throws ParseException {

        Optional<Task> taskFound = taskRepo.findByTaskIdAndProject(taskId, projectRepo.findByProjectId(projectId));

        if(taskFound.isPresent()) {
            Task existingTask = taskFound.get();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (HashMap.Entry<String, Object> entry : updatesTask.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                switch (fieldName) {
                    case "taskName":
                        existingTask.setTaskName((String) fieldValue);
                        break;
                    case "dueDate":
                        existingTask.setDueDate(new Date(dateFormat.parse((String) fieldValue).getTime()));
                        break;
                    case "description":
                        existingTask.setDescription((String) fieldValue);
                        break;
                    case "taskStatus":
                        existingTask.setTaskStatus(TaskStatus.valueOf((String)fieldValue));
                        break;
                    case "priorityStatus":
                        existingTask.setPriorityStatus(PriorityStatus.valueOf((String)fieldValue));
                        break;
                    case "teamMember":
                        if (!isAssignToProject((String)fieldValue, projectId))
                            return "Not Authorize to Add New Task in This Project.";
                        taskAssignmentRepo.deleteByTask(taskRepo.findByTaskId(taskId));
                        TaskAssignment taskAssignment = new TaskAssignment(teamMemberRepo.findByUsername((String)fieldValue), taskRepo.findByTaskId(taskId));
                        taskAssignmentRepo.save(taskAssignment);
                        break;
                }
            }

            taskRepo.save(existingTask);
            return  "["+existingTask.getTaskId()+"] Task Updated Successfully.";

        }
        return "Task Not Found.";

    }

    @Override
    public List<ListOfTaskDOT> getTasks(String username) {
        return taskRepo.findByUsername(username);
    }

    @Override
    public String deleteTask(Integer projectId, Integer taskId) {

        Optional<Task> taskFound = taskRepo.findByTaskIdAndProject(taskId, projectRepo.findByProjectId(projectId));

        if(taskFound.isPresent()) {
            taskRepo.deleteById(taskId);
            return  "["+taskFound.get().getTaskId()+"] Task Deleted Successfully.";
        }

        return "Project Not Found.";

    }

    @Override
    public boolean isAssignToProject(String teamMember, Integer projectId) {

        if(projectAssignmentRepo.findByTeamMemberAndProject(teamMemberRepo.findByUsername(teamMember), projectRepo.findByProjectId(projectId)).isPresent())
            return true;
        return false;

    }

}
