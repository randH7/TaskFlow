package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.DTO.ProjectDTO;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface ProjectService {

    void createProject(String mangerUsername, ProjectDTO newProject);

    String editProject(String mangerUsername, String projectId, HashMap<String, Object> updatesProject) throws ParseException;

    List<ListOfProjectsDTO> getProjects(String username, String typeRole);

    boolean isMangerForProject(String mangerUsername, String projectId);

    String deleteProject(String projectId);

}
