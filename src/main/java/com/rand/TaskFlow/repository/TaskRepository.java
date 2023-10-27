package com.rand.TaskFlow.repository;

import com.rand.TaskFlow.DOT.ListOfTaskDOT;
import com.rand.TaskFlow.entity.Project;
import com.rand.TaskFlow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT new com.rand.TaskFlow.DOT.ListOfTaskDOT(t.taskName, t.startDate, t.dueDate, t.description, t.taskStatus, t.priorityStatus, ta.teamMember) FROM Task t JOIN TaskAssignment ta ON t.taskId = ta.task.taskId WHERE ta.teamMember.username = ?1")
    List<ListOfTaskDOT> findByUsername(String username);

    Optional<Task> findByTaskIdAndProject(Integer taskId, Project project);

    Task findByTaskId(Integer taskId);

    // for test purpose
    Task findTopByOrderByTaskIdDesc();
}
