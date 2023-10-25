package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.DOT.ProjectDOT;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProjectService {

    void createProject(String mangerUsername,ProjectDOT newProject);

    String editProject(String mangerUsername, String projectId, HashMap<String, Object> updatesProject) throws ParseException;

    List<ListOfProjectsDOT> getProjects(String username, String typeRole);

    boolean isMangerForProject(String mangerUsername, String projectId);
}
