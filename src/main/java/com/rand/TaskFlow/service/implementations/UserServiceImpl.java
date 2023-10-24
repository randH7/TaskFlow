package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.entity.Manger;
import com.rand.TaskFlow.entity.TeamMember;
import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.repository.MangerRepository;
import com.rand.TaskFlow.repository.TeamMemberRepository;
import com.rand.TaskFlow.repository.UserRepository;
import com.rand.TaskFlow.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MangerRepository mangerRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Override
    public void signUpUser(User user, String userType) {
        if(userType.equals("manger")){
            Manger manger = new Manger();
            manger.setUsername(user.getUsername());
            manger.setEmail(user.getEmail());
            manger.setPassword(user.getPassword());
            manger.setEmployName(user.getEmployName());
            manger.setJobTitle(user.getJobTitle());
            manger.setActive(true);
            mangerRepo.save(manger);
        } else if (userType.equals("teamMember")) {
            TeamMember teamMember = new TeamMember();
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

}
