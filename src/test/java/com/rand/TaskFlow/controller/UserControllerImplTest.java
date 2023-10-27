package com.rand.TaskFlow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.repository.MangerRepository;
import com.rand.TaskFlow.repository.RoleRepository;
import com.rand.TaskFlow.repository.TeamMemberRepository;
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
class UserControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private MangerRepository mangerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Set<Role> authoritiesM = new HashSet<>();
        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        Manger manger = new Manger("testUser", "testUser@g.com", passwordEncoder.encode("12345"), "Rand", "manger", false, authoritiesM);
        mangerRepo.save(manger);

        Set<Role> authoritiesT = new HashSet<>();
        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        TeamMember teamMember1 = new TeamMember("teamMember1", "teamMember1@g.com", passwordEncoder.encode("12345"), "Ali", "developer", false, authoritiesT, false);
        teamMemberRepo.save(teamMember1);

    }


    @AfterEach
    void tearDown() {
        mangerRepo.deleteAll();
        teamMemberRepo.deleteAll();
        userRepo.deleteAll();
    }


    @Test
    void post_Valid_SignUpManger() throws Exception {

        Set<Role> authoritiesM = new HashSet<>();
        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        User manager = new User("manager1", "manager1@hotmail.com", "12345", "Maha", "Project manager", false, authoritiesM);
        String body = objectMapper.writeValueAsString(manager);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manger")
                ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-up successful. Now Sign-in"));
        assertEquals(3, userRepo.count());
        assertEquals(2, mangerRepo.count());
    }

    @Test
    void post_InvalidUsername_SignUpManger() throws Exception {

        Set<Role> authoritiesM = new HashSet<>();
        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        User manager = new User("testUser", "manager1@hotmail.com", "12345", "Maha", "Project manager", false, authoritiesM);
        String body = objectMapper.writeValueAsString(manager);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manger")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Username already exists"));
        assertEquals(2, userRepo.count());
        assertEquals(1, mangerRepo.count());
    }

    @Test
    void post_InvalidEmail_SignUpManger() throws Exception {

        Set<Role> authoritiesM = new HashSet<>();
        authoritiesM.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
        User manager = new User("manger1", "testUser@g.com", "12345", "Maha", "Project manager", false, authoritiesM);
        String body = objectMapper.writeValueAsString(manager);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "manger")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Email already exists"));
        assertEquals(2, userRepo.count());
        assertEquals(1, mangerRepo.count());
    }

    @Test
    void post_Valid_SignUpTeamMember() throws Exception {

        Set<Role> authoritiesT = new HashSet<>();
        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        User teamMember = new User("teamMember", "teamMember@hotmail.com", "12345", "Basil", "Web developer", false, authoritiesT);
        String body = objectMapper.writeValueAsString(teamMember);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "teamMember")
                ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-up successful. Now Sign-in"));
        assertEquals(3, userRepo.count());
        assertEquals(2, teamMemberRepo.count());
    }

    @Test
    void post_InvalidUsername_SignUpTeamMember() throws Exception {

        Set<Role> authoritiesT = new HashSet<>();
        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        User teamMember = new User("teamMember1", "teamMember@hotmail.com", "12345", "Basil", "Web developer", false, authoritiesT);
        String body = objectMapper.writeValueAsString(teamMember);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "teamMember")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Username already exists"));
        assertEquals(2, userRepo.count());
        assertEquals(1, teamMemberRepo.count());
    }

    @Test
    void post_InvalidEmail_SignUpTeamMember() throws Exception {

        Set<Role> authoritiesT = new HashSet<>();
        authoritiesT.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
        User teamMember = new User("teamMember", "teamMember1@g.com", "12345", "Basil", "Web developer", false, authoritiesT);
        String body = objectMapper.writeValueAsString(teamMember);

        // Perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/taskflow/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userType", "teamMember")
                ).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Email already exists"));
        assertEquals(2, userRepo.count());
        assertEquals(1, teamMemberRepo.count());
    }

    @Test
    void get_Valid_SignInManager() throws Exception {

        // Perform the GET request
        MvcResult mvcResult = mockMvc.perform(get("/taskflow/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("usernameOrEmail", "testUser")
                        .param("password", "12345")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-in successful"));

    }

    @Test
    void get_Valid_SignInTeamMember() throws Exception {

        // Perform the GET request
        MvcResult mvcResult = mockMvc.perform(get("/taskflow/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("usernameOrEmail", "teamMember1")
                        .param("password", "12345")
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sign-in successful"));

    }

}