package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DTO.AuthenticationDTO;
import com.rand.TaskFlow.DTO.UserLoginDTO;
import com.rand.TaskFlow.entity.Manager;
import com.rand.TaskFlow.entity.enums.Role;
import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.filters.JwtService;
import com.rand.TaskFlow.repository.ManagerRepository;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.repository.UserRepository;
import com.rand.TaskFlow.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployRepository employRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public void signUpUser(User user, String userType) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userType.toLowerCase().equals("manager")){
            Manager manager = new Manager(user.getUsername(), user.getEmail(), user.getPassword(), user.getEmployName(), user.getJobTitle(), Role.ROLE_MANAGER);
            managerRepo.save(manager);
        } else if (userType.toLowerCase().equals("employ")) {
            Employ employ = new Employ(user.getUsername(), user.getEmail(), user.getPassword(), user.getEmployName(), user.getJobTitle(), Role.ROLE_EMPLOY);
            employRepo.save(employ);
        }

    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public AuthenticationDTO loginUser(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        User user = userRepo.findById(userLoginDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationDTO.builder().access_token(jwtToken).build();
    }

}
