package com.rand.TaskFlow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rand.TaskFlow.DOT.ListOfProjectsDOT;
import com.rand.TaskFlow.entity.Manger;
import com.rand.TaskFlow.entity.ProjectStatus;
import com.rand.TaskFlow.entity.Role;
import com.rand.TaskFlow.entity.TeamMember;
import com.rand.TaskFlow.repository.MangerRepository;
import com.rand.TaskFlow.repository.RoleRepository;
import com.rand.TaskFlow.repository.TeamMemberRepository;
import com.rand.TaskFlow.repository.UserRepository;
import com.rand.TaskFlow.service.interfaces.ProjectService;
import org.apache.catalina.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class ProjectControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleRepository roleRepo;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAll_Valid_AllProjects() throws Exception {

        // Mock the authentication and the service call
        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);

        // Mock data
        List<ListOfProjectsDOT> projects = new ArrayList<>();
        projects.add(createSampleListOfProjects());
        when(projectService.getProjects("testUser", "ROLE_MANAGER")).thenReturn(projects);

        // Perform the GET request
        mockMvc.perform(get("/taskflow/projects"))
                .andExpect(status().isOk())
                .andExpect(content().string("List of All projects: \n" + createSampleListOfProjects().toString() + "\n"));
    }

    private ListOfProjectsDOT createSampleListOfProjects() {
        ListOfProjectsDOT project = new ListOfProjectsDOT();
        project.setProjectName("Sample Project");
        project.setStartDate(new Date(2024-10-10));
        project.setDueDate(new Date(2025-10-10));
        Set<Role> authorities = new HashSet<>();
        authorities.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        project.setManger(new Manger("testUser", "testUser@g.com", "12345", "Rand", "manger", true, authorities));
        Set<Role> authorities2 = new HashSet<>();
        authorities2.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        project.setLeader(new TeamMember("testUser", "testUser@g.com", "12345", "Ali", "developer", true, authorities, false));
        project.setProjectStatus(ProjectStatus.IN_PROGRESS);

        return project;
    }

}