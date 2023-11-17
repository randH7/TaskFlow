package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.DetailsProject.DetailsProjectEmployeesDTO;
import com.rand.TaskFlow.DTO.ListOfProjectsDTO;
import com.rand.TaskFlow.DTO.AddProjectDTO;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface ProjectService {

    void createProject(String mangerUsername, AddProjectDTO newProject) throws ParseException;

    String editProject(String mangerUsername, Integer projectId, HashMap<String, Object> updatesProject) throws ParseException;

    List<ListOfProjectsDTO> getProjects(String username, String typeRole);

    DetailsProjectEmployeesDTO getProjectDetails(String username, String typeRole, Integer projectId);

    String deleteProject(Integer projectId);

}
