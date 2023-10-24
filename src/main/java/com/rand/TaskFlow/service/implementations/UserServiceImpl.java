package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.entity.Manger;
import com.rand.TaskFlow.entity.Role;
import com.rand.TaskFlow.entity.TeamMember;
import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.repository.MangerRepository;
import com.rand.TaskFlow.repository.RoleRepository;
import com.rand.TaskFlow.repository.TeamMemberRepository;
import com.rand.TaskFlow.repository.UserRepository;
import com.rand.TaskFlow.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MangerRepository mangerRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUpUser(User user, String userType) {

        Set<Role> userRoles = new HashSet<>();

        if ("TEAMMEMBER".equals(userType.toUpperCase())) {
            userRoles.add(roleRepo.findByName("TEAMMEMBER"));
        } else if ("MANAGER".equals(userType.toUpperCase())) {
            userRoles.add(roleRepo.findByName("MANAGER"));
        }

        user.setRoles(userRoles);

        if(userType.toLowerCase().equals("manger")){
            Manger manger = new Manger();
            manger.setRoles(user.getRoles());
            manger.setUsername(user.getUsername());
            manger.setEmail(user.getEmail());
            manger.setPassword(user.getPassword());
            manger.setEmployName(user.getEmployName());
            manger.setJobTitle(user.getJobTitle());
            manger.setActive(true);
            mangerRepo.save(manger);
        } else if (userType.toLowerCase().equals("teamMember")) {
            TeamMember teamMember = new TeamMember();
            teamMember.setRoles(user.getRoles());
            teamMember.setUsername(user.getUsername());
            teamMember.setEmail(user.getEmail());
            teamMember.setPassword(user.getPassword());
            teamMember.setEmployName(user.getEmployName());
            teamMember.setJobTitle(user.getJobTitle());
            teamMember.setActive(true);
            teamMember.setLeader(false);
            teamMemberRepo.save(teamMember);
        }
    }

    public boolean isUsernameTaken(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepo.findByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public boolean isPasswordValid(User userFound, String password) {

        if(userFound.getPassword().equals(password)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user with the given username
        User user = userRepo.findByUsername(username);
        // Check if user exists
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            // Create a collection of SimpleGrantedAuthority objects from the user's roles
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            // Return the user details, including the username, password, and authorities
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        }
    }

}
