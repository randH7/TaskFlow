//package com.rand.TaskFlow.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rand.TaskFlow.DTO.TaskDTO;
//import com.rand.TaskFlow.entity.*;
//import com.rand.TaskFlow.entity.enums.PriorityStatus;
//import com.rand.TaskFlow.entity.enums.Role;
//import com.rand.TaskFlow.entity.enums.TaskStatus;
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
//@SpringBootTest
//@AutoConfigureMockMvc
//class TaskControllerImplTest {
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
//    private TaskRepository taskRepo;
//
//    @Autowired
//    private TaskAssignmentRepository taskAssignmentRepo;
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
//        Project sampleProject = new Project("Sample Project", manager, leader, new Date(dateFormat.parse("2024-10-10").getTime()), new Date(dateFormat.parse("2025-10-10").getTime()), "description", IN_PROGRESS);
//        projectRepo.save(sampleProject);
//        ProjectAssignment projectAssignment1 = new ProjectAssignment(employ1, sampleProject);
//        ProjectAssignment projectAssignment2 = new ProjectAssignment(employ2, sampleProject);
//        projectAssignmentRepo.saveAll(List.of(projectAssignment1, projectAssignment2));
//
//        Task task1 = new Task("Sample Task 1", sampleProject, new Date(dateFormat.parse("2024-10-20").getTime()), new Date(dateFormat.parse("2024-10-25").getTime()), "Description sample task 1", TaskStatus.TO_DO, PriorityStatus.MEDIUM);
//        Task task2 = new Task("Sample Task 2", sampleProject, new Date(dateFormat.parse("2024-10-20").getTime()), new Date(dateFormat.parse("2024-10-22").getTime()), "Description sample task 2", TaskStatus.IN_PROGRESS, PriorityStatus.HIGH);
//        taskRepo.saveAll(List.of(task1, task2));
//        TaskAssignment taskAssignment1 = new TaskAssignment(teamMemberRepo.findByUsername("employ1"), task1);
//        TaskAssignment taskAssignment2 = new TaskAssignment(teamMemberRepo.findByUsername("employ1"), task2);
//        taskAssignmentRepo.saveAll(List.of(taskAssignment1, taskAssignment2));
//
//    }
//
//
//    @AfterEach
//    void tearDown() {
//        taskAssignmentRepo.deleteAll();
//        taskRepo.deleteAll();
//        projectAssignmentRepo.deleteAll();
//        projectRepo.deleteAll();
//        userRepo.deleteAll();
//    }
//
//
//    @Test
//    void post_Valid_AddTask() throws Exception {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        TaskDTO taskDTO = new TaskDTO("Sample Task 1", new Date(dateFormat.parse("2024-10-29").getTime()),  new Date(dateFormat.parse("2024-11-07").getTime()),  "Description sample task 1", TaskStatus.TO_DO, PriorityStatus.LOW, "teamMember2");
//
//        String body = objectMapper.writeValueAsString(taskDTO);
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("teamMember1", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEAM_MEMBER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        Project project = projectRepo.findTopByOrderByProjectIdDesc();
//        Integer projectId = project.getProjectId();
//
//        // Perform the POST request
//        MvcResult mvcResult = mockMvc.perform(post("/taskflow/projects/{projectId}/add-task", projectId)
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("["+ taskDTO.getTaskName()+"] Task Created Successfully. Now Let's Start"));
//        assertEquals(3, taskRepo.count());
//        assertEquals(3, taskAssignmentRepo.count());
//    }
//
//    @Test
//    void patch_Valid_UpdatedTask() throws Exception {
//
//        HashMap<String, Object> updatesTask = new HashMap<>();
//        updatesTask.put("priorityStatus", "LOW");
//
//        String body = objectMapper.writeValueAsString(updatesTask);
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("teamMember2", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEAM_MEMBER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        Project project = projectRepo.findTopByOrderByProjectIdDesc();
//        Integer projectId = project.getProjectId();
//        Task task = taskRepo.findTopByOrderByTaskIdDesc();
//        Integer taskId = task.getTaskId();
//
//        // Perform the PATCH request
//        MvcResult mvcResult = mockMvc.perform(patch("/taskflow/projects/{projectId}/edit-tasks/{taskId}", projectId, taskId)
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isAccepted())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("["+taskId+"] Task Updated Successfully."));
//        assertEquals("LOW", taskRepo.findTopByOrderByTaskIdDesc().getPriorityStatus().toString());
//    }
//
//    @Test
//    void get_Valid_MyTasks() throws Exception {
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("teamMember1", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEAM_MEMBER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        // Perform the GET request
//        MvcResult mvcResult = mockMvc.perform(get("/taskflow/my-tasks"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("List of All Yours Tasks:"));
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Task 1"));
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sample Task 2"));
//    }
//
//    @Test
//    void delete_Valid_DeletedTask() throws Exception {
//
//        // Mock the authentication
//        Authentication auth = new UsernamePasswordAuthenticationToken("teamMember2", null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEAM_MEMBER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        Project project = projectRepo.findTopByOrderByProjectIdDesc();
//        Integer projectId = project.getProjectId();
//        Task task = taskRepo.findTopByOrderByTaskIdDesc();
//        Integer taskId = task.getTaskId();
//
//        // Perform the DELETE request
//        MvcResult mvcResult = mockMvc.perform(delete("/taskflow/projects/{projectId}/delete-tasks/{taskId}", projectId, taskId))
//                .andExpect(status().isAccepted())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("["+taskId+"] Task Deleted Successfully."));
//        assertEquals(1, taskRepo.count());
//        assertEquals(1, taskAssignmentRepo.count());
//    }
//
//}