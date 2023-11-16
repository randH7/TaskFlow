package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.AuthenticationDTO;
import com.rand.TaskFlow.DTO.UserLoginDTO;
import com.rand.TaskFlow.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signUpUser(User user, String userType);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    AuthenticationDTO loginUser(UserLoginDTO userLoginDTO);

}
