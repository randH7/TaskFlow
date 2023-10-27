package com.rand.TaskFlow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rand.TaskFlow.DOT.ProjectDOT;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.rand.TaskFlow.entity.ProjectStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private MangerRepository mangerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() throws ParseException {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Set<Role> authoritiesM = new HashSet<>();
        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        Manger manger = new Manger("testUser", "testUser@g.com", "12345", "Rand", "manger", true, authoritiesM);
        mangerRepo.save(manger);

        Set<Role> authoritiesT = new HashSet<>();
        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        TeamMember leader = new TeamMember("testUserTM", "testUserTM@g.com", "12345", "Ali", "developer", true, authoritiesT, false);
        TeamMember teamMember1 = new TeamMember("teamMember1", "teamMember1@g.com", "12345", "Ali", "developer", true, authoritiesT, false);
        TeamMember teamMember2 = new TeamMember("teamMember2", "teamMember2@g.com", "12345", "Ali", "developer", true, authoritiesT, false);
        teamMemberRepo.saveAll(List.of(leader, teamMember1, teamMember2));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Project sampleProject1 = new Project("Sample Project", manger, leader, new Date(dateFormat.parse("2024-10-10").getTime()), new Date(dateFormat.parse("2025-10-10").getTime()), "description", IN_PROGRESS);
        Project sampleProject2 = new Project("Sample Project 2", manger, leader, new Date(dateFormat.parse("2024-07-10").getTime()), new Date(dateFormat.parse("2025-07-10").getTime()), "description 2", IN_PROGRESS);
        projectRepo.saveAll(List.of(sampleProject1, sampleProject2));
        ProjectAssignment projectAssignment1 = new ProjectAssignment(teamMember1, sampleProject1);
        ProjectAssignment projectAssignment2 = new ProjectAssignment(teamMember2, sampleProject2);
        ProjectAssignment projectAssignmentL1 = new ProjectAssignment(leader, sampleProject1);
        ProjectAssignment projectAssignmentL2 = new ProjectAssignment(leader, sampleProject2);
        projectAssignmentRepo.saveAll(List.of(projectAssignment1, projectAssignment2, projectAssignmentL1, projectAssignmentL2));

    }


    @AfterEach
    void tearDown() {
        projectAssignmentRepo.deleteAll();
        projectRepo.deleteAll();
        userRepo.deleteAll();
    }


    @Test
    void post_Valid_CreatedProject() throws Exception {

        List<String> teamMembersUsername = new ArrayList<> ();
        teamMembersUsername.add("teamMember1");
        teamMembersUsername.add("teamMember2");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ProjectDOT projectDOT = new ProjectDOT("SDA web", "teamMember1",  new Date(dateFormat.parse("2024-10-10").getTime()),  new Date(dateFormat.parse("2024-10-15").getTime()), "SDA web.", IN_PROGRESS, teamMembersUsername);

        String body = objectMapper.writeValueAsString(projectDOT);

        // Mock the authentication
        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/projects/create-project")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("["+projectDOT.getProjectName()+"] Project Created Successfully. Now Let's Start"));
        assertEquals(3, projectRepo.count());
        assertEquals(6, projectAssignmentRepo.count());
    }

    @Test
    void patch_Valid_UpdatedProject() throws Exception {

        HashMap<String, Object> updatesProject = new HashMap<>();
        updatesProject.put("leaderUsername", "teamMember2");

        String body = objectMapper.writeValueAsString(updatesProject);

        // Mock the authentication
        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        Project project = projectRepo.findTopByOrderByProjectIdDesc();
        Integer projectId = project.getProjectId();

        // Perform the PATCH request
        MvcResult mvcResult = mockMvc.perform(patch("/taskflow/projects/edit-project/{projectId}", projectId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("[Sample Project 2] Project Updated Successfully."));
        assertEquals("teamMember2", projectRepo.findTopByOrderByProjectIdDesc().getLeader().getUsername());
    }

    @Test
    void get_Valid_AllProjects() throws Exception {

        // Mock the authentication
        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Perform the GET request
        MvcResult mvcResult = mockMvc.perform(get("/taskflow/projects"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("List of All projects:"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Project"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Project 2"));
    }

    @Test
    void delete_Valid_DeletedProject() throws Exception {

        // Mock the authentication
        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        Project project = projectRepo.findTopByOrderByProjectIdDesc();
        Integer projectId = project.getProjectId();

        // Perform the DELETE request
        MvcResult mvcResult = mockMvc.perform(delete("/taskflow/projects/delete-project/{projectId}", projectId))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("[Sample Project 2] Project Deleted Successfully."));
        assertEquals(1, projectRepo.count());
        assertEquals(2, projectAssignmentRepo.count());
    }

}