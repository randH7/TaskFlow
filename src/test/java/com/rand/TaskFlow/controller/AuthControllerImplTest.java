package com.rand.TaskFlow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rand.TaskFlow.DTO.UserLoginDTO;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.entity.enums.Role;
import com.rand.TaskFlow.repository.ManagerRepository;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.repository.ProjectRepository;
import com.rand.TaskFlow.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ManagerRepository mangerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmployRepository teamMemberRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Manager manager = new Manager("testUser", "testUser@g.com", passwordEncoder.encode("12345"), "Rand", "manager", Role.ROLE_MANAGER);
        mangerRepo.save(manager);

        Employ employ1 = new Employ("employ1", "employ1@g.com", passwordEncoder.encode("12345"), "Ali", "developer", Role.ROLE_EMPLOY);
        teamMemberRepo.save(employ1);

    }

    @AfterEach
    void tearDown() {
        projectRepo.deleteAll();
        mangerRepo.deleteAll();
        teamMemberRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void post_Valid_SignUpManger() throws Exception {

        Map<String, Object> manager = new HashMap<>();
        manager.put("username", "manager1");
        manager.put("email", "manager1@hotmail.com");
        manager.put("password", "12345");
        manager.put("employName", "Maha");
        manager.put("jobTitle", "Project manager");
        manager.put("role", Role.ROLE_MANAGER);

        String body = objectMapper.writeValueAsString(manager);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manager")
                ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-up successful. Now Sign-in"));
        assertEquals(countBefore + 1, countAfter, "The number of rows in the database should increase by 1 after the test");

    }

    @Test
    void post_InvalidUsername_SignUpManger() throws Exception {

        Map<String, Object> manager = new HashMap<>();
        manager.put("username", "testUser");
        manager.put("email", "manager1@hotmail.com");
        manager.put("password", "12345");
        manager.put("employName", "Maha");
        manager.put("jobTitle", "Project manager");
        manager.put("role", Role.ROLE_MANAGER);

        String body = objectMapper.writeValueAsString(manager);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manager")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Username already exists"));
        assertEquals(countBefore, countAfter, "The number of rows in the database should be the same after the test");

    }

    @Test
    void post_InvalidEmail_SignUpManger() throws Exception {

        Map<String, Object> manager = new HashMap<>();
        manager.put("username", "manger1");
        manager.put("email", "testUser@g.com");
        manager.put("password", "12345");
        manager.put("employName", "Maha");
        manager.put("jobTitle", "Project manager");
        manager.put("role", Role.ROLE_MANAGER);

        String body = objectMapper.writeValueAsString(manager);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manager")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Email already exists"));
        assertEquals(countBefore, countAfter, "The number of rows in the database should be the same after the test");

    }

    @Test
    void post_Valid_SignUpTeamMember() throws Exception {

        Map<String, Object> teamMember = new HashMap<>();
        teamMember.put("username", "teamMember");
        teamMember.put("email", "teamMember@hotmail.com");
        teamMember.put("password", "12345");
        teamMember.put("employName", "Basil");
        teamMember.put("jobTitle", "Web developer");
        teamMember.put("role", Role.ROLE_EMPLOY);

        String body = objectMapper.writeValueAsString(teamMember);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "employ")
                ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-up successful. Now Sign-in"));
        assertEquals(countBefore + 1, countAfter, "The number of rows in the database should increase by 1 after the test");

    }

    @Test
    void post_InvalidUsername_SignUpTeamMember() throws Exception {

        Map<String, Object> employ = new HashMap<>();
        employ.put("username", "employ1");
        employ.put("email", "employ@hotmail.com");
        employ.put("password", "12345");
        employ.put("employName", "Basil");
        employ.put("jobTitle", "Web developer");
        employ.put("role", Role.ROLE_EMPLOY);

        String body = objectMapper.writeValueAsString(employ);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "employ")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Username already exists"));
        assertEquals(countBefore, countAfter, "The number of rows in the database should be the same after the test");

    }

    @Test
    void post_InvalidEmail_SignUpTeamMember() throws Exception {

        Map<String, Object> employ = new HashMap<>();
        employ.put("username", "employ");
        employ.put("email", "employ1@g.com");
        employ.put("password", "12345");
        employ.put("employName", "Basil");
        employ.put("jobTitle", "Web developer");
        employ.put("role", Role.ROLE_EMPLOY);

        String body = objectMapper.writeValueAsString(employ);

        long countBefore = userRepo.count();

        MvcResult mvcResult = mockMvc.perform(post("/auth/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "employ")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        long countAfter = userRepo.count();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Email already exists"));
        assertEquals(countBefore, countAfter, "The number of rows in the database should be the same after the test");

    }

    @Test
    void get_Valid_SignInManager() throws Exception {

        UserLoginDTO userLoginDTO = new UserLoginDTO("testUser", "12345");
        String body = objectMapper.writeValueAsString(userLoginDTO);

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("access_token"));

    }

    @Test
    void get_Valid_SignInTeamMember() throws Exception {

        UserLoginDTO userLoginDTO = new UserLoginDTO("employ1", "12345");
        String body = objectMapper.writeValueAsString(userLoginDTO);

        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("access_token"));

    }

}