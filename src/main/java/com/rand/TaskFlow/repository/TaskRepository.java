package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskDTO;
import com.rand.TaskFlow.DTO.ListOfTaskDTO;
import com.rand.TaskFlow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT new com.rand.TaskFlow.DTO.ListOfTaskDTO(t.taskName, t.dueDate, t.taskStatus, t.priorityStatus) FROM Task t JOIN TaskAssignment ta ON t.taskId = ta.task.taskId WHERE ta.employ.username = ?1")
    List<ListOfTaskDTO> findByUsername(String username);

    //@Query("SELECT new com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskDTO(t.taskName, t.project.projectName, t.startDate, t.dueDate, t.taskStatus, t.priorityStatus, t.description) FROM Task t JOIN TaskAssignment ta ON t.taskId = ta.task.taskId JOIN Project p ON t.project.projectId = p.projectId WHERE t.taskId = ?1 AND ta.employ.employName = ?2")
    @Query("SELECT new com.rand.TaskFlow.DTO.DetailsTask.DetailsTaskDTO(t.taskName, t.project.projectName, t.startDate, t.dueDate, t.taskStatus, t.priorityStatus, t.description) FROM Task t JOIN TaskAssignment ta ON t.taskId = ta.task.taskId JOIN Project p ON t.project.projectId = p.projectId WHERE t.taskId = ?1 AND ta.employ.username = ?2")
    DetailsTaskDTO findDetailsById(Integer taskId, String userName);

    // for test purpose
    Task findTopByOrderByTaskIdDesc();

}
