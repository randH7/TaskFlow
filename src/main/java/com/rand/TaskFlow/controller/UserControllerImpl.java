package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskflow")
public class UserControllerImpl {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> processRegistration(@RequestBody @Valid User user, @RequestParam("userType") String userType){

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

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> processSignIn(@RequestParam("usernameOrEmail") @Valid String usernameOrEmail, @RequestParam("password") @Valid String password){

        return ResponseEntity.status(HttpStatus.OK).body("Sign-in successful");

    }

    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public String showDashboardPage(){
        return "Dashboard Page";
    }

}
