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

import java.util.*;

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

        Set<Role> authorities = new HashSet<>();

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userType.toLowerCase().equals("manger")){
            authorities.add(roleRepo.findByAuthority("ROLE_MANAGER").get());
            Manger manger = new Manger(user.getUsername(), user.getEmail(), user.getPassword(), user.getEmployName(), user.getJobTitle(), true, authorities);
            mangerRepo.save(manger);
        } else if (userType.toLowerCase().equals("teammember")) {
            authorities.add(roleRepo.findByAuthority("ROLE_TEAM_MEMBER").get());
            TeamMember teamMember = new TeamMember(user.getUsername(), user.getEmail(), user.getPassword(), user.getEmployName(), user.getJobTitle(), true, authorities, false);
            teamMemberRepo.save(teamMember);
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user with the given username
        User user = userRepo.findByUsername(username);
        // Check if user exists
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            // Create a collection of SimpleGrantedAuthority objects from the user's roles
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getAuthorities().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            });
            // Return the user details, including the username, password, and authorities
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        }
    }

}
