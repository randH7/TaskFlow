package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.AuthenticationDTO;
import com.rand.TaskFlow.DTO.UserLoginDTO;
import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.service.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControllerImpl {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody @Valid User user, @RequestParam("userType") String userType) {

        if(userService.isUsernameTaken(user.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");

        if (userService.isEmailTaken(user.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");

        try{
            userService.signUpUser(user, userType);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sign-up successful. Now Sign-in");
        }catch (Exception e){
            String messageError = "user Not registered Successfully. ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationDTO> login (@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(userLoginDTO));
    }

}
