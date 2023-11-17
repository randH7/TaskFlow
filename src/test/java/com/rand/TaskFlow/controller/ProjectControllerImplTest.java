//package com.rand.TaskFlow.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rand.TaskFlow.DTO.AddProjectDTO;
//import com.rand.TaskFlow.entity.*;
//import com.rand.TaskFlow.entity.enums.Role;
//import com.rand.TaskFlow.repository.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static com.rand.TaskFlow.entity.enums.ProjectStatus.IN_PROGRESS;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ProjectControllerImplTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ProjectRepository projectRepo;
//
//    @Autowired
//    private ProjectAssignmentRepository projectAssignmentRepo;
//
//    @Autowired
//    private RoleRepository roleRepo;
//
//    @Autowired
//    private ManagerRepository mangerRepo;
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private EmployRepository teamMemberRepo;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @BeforeEach
//    public void setUp() throws ParseException {
//
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        Set<Role> authoritiesM = new HashSet<>();
//        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
//        Manager manager = new Manager("testUser", "testUser@g.com", "12345", "Rand", "manager", true, authoritiesM);
//        mangerRepo.save(manager);
//
//        Set<Role> authoritiesT = new HashSet<>();
//        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
//        Employ leader = new Employ("testUserTM", "testUserTM@g.com", "12345", "Ali", "developer", true, authoritiesT);
//        Employ employ1 = new Employ("employ1", "employ1@g.com", "12345", "Ali", "developer", true, authoritiesT);
//        Employ employ2 = new Employ("employ2", "employ2@g.com", "12345", "Ali", "developer", true, authoritiesT);
//        teamMemberRepo.saveAll(List.of(leader, employ1, employ2));
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Project sampleProject1 = new Project("Sample Project", manager, leader, new Date(dateFormat.parse("2024-10-10").getTime()), new Date(dateFormat.parse("2025-10-10").getTime()), "description", IN_PROGRESS);
//        Project sampleProject2 = new Project("Sample Project 2", manager, leader, new Date(dateFormat.parse("2024-07-10").getTime()), new Date(dateFormat.parse("2025-07-10").getTime()), "description 2", IN_PROGRESS);
//        projectRepo.saveAll(List.of(sampleProject1, sampleProject2));
//        ProjectAssignment projectAssignment1 = new ProjectAssignment(employ1, sampleProject1);
//        ProjectAssignment projectAssignment2 = new ProjectAssignment(employ2, sampleProject2);
//        ProjectAssignment projectAssignmentL1 = new ProjectAssignment(leader, sampleProject1);
//        ProjectAssignment projectAssignmentL2 = new ProjectAssignment(leader, sampleProject2);
//        projectAssignmentRepo.saveAll(List.of(projectAssignment1, projectAssignment2, projectAssignmentL1, projectAssignmentL2));
//
//    }
//
//
//    @AfterEach
//    void tearDown() {
//        projectAssignmentRepo.deleteAll();
//        projectRepo.deleteAll();
//        userRepo.deleteAll();
//    }
//
//
//    @Test
//    void post_Valid_CreatedProject() throws Exception {
//
//        List<String> teamMembersUsername = new ArrayList<> ();
//        teamMembersUsername.add("teamMember1");
//        teamMembersUsername.add("teamMember2");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        AddProjectDTO projectDTO = new AddProjectDTO("SDA web", "teamMember1",  new Date(dateFormat.parse("2024-10-10").getTime()),  new Date(dateFormat.parse("2024-10-15").getTime()), "SDA web.", IN_PROGRESS, teamMembersUsername);
//
//        String body = objectMapper.writeValueAsString(projectDTO);
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        // Perform the POST request
//        MvcResult mvcResult = mockMvc.perform(post("/taskflow/projects/create-project")
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("["+ projectDTO.getProjectName()+"] Project Created Successfully. Now Let's Start"));
//        assertEquals(3, projectRepo.count());
//        assertEquals(6, projectAssignmentRepo.count());
//    }
//
//    @Test
//    void patch_Valid_UpdatedProject() throws Exception {
//
//        HashMap<String, Object> updatesProject = new HashMap<>();
//        updatesProject.put("leaderUsername", "teamMember2");
//
//        String body = objectMapper.writeValueAsString(updatesProject);
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        Project project = projectRepo.findTopByOrderByProjectIdDesc();
//        Integer projectId = project.getProjectId();
//
//        // Perform the PATCH request
//        MvcResult mvcResult = mockMvc.perform(patch("/taskflow/projects/edit-project/{projectId}", projectId)
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isAccepted())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("[Sample Project 2] Project Updated Successfully."));
//        assertEquals("teamMember2", projectRepo.findTopByOrderByProjectIdDesc().getLeader().getUsername());
//    }
//
//    @Test
//    void get_Valid_AllProjects() throws Exception {
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        // Perform the GET request
//        MvcResult mvcResult = mockMvc.perform(get("/taskflow/projects"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("List of All projects:"));
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Project"));
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Project 2"));
//    }
//
//    @Test
//    void delete_Valid_DeletedProject() throws Exception {
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("testUser", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        Project project = projectRepo.findTopByOrderByProjectIdDesc();
//        Integer projectId = project.getProjectId();
//
//        // Perform the DELETE request
//        MvcResult mvcResult = mockMvc.perform(delete("/taskflow/projects/delete-project/{projectId}", projectId))
//                .andExpect(status().isAccepted())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("[Sample Project 2] Project Deleted Successfully."));
//        assertEquals(1, projectRepo.count());
//        assertEquals(2, projectAssignmentRepo.count());
//    }
//
//}