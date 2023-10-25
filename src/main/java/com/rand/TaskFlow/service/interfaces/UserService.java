package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signUpUser(User user, String userType);

    User getUserByUsernameOrEmail(String usernameOrEmail);

    boolean isPasswordValid(User userFound, String password);
}
