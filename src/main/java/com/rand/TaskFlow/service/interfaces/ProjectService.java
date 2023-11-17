package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.DTO.ProjectDTO;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface ProjectService {

    void createProject(String mangerUsername, ProjectDTO newProject) throws ParseException;

    String editProject(String mangerUsername, Integer projectId, HashMap<String, Object> updatesProject) throws ParseException;

    List<ListOfProjectsDTO> getProjects(String username, String typeRole);

    String deleteProject(Integer projectId);

}
