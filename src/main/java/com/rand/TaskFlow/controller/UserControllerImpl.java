package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.request.SignInRequest;
import com.rand.TaskFlow.service.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerImpl {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/taskflow/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public String showRegistrationForm(){
        return "Sign-up Form";
    }

    @PostMapping("/taskflow/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> processRegistration(@RequestBody @Valid User user, @RequestParam("userType") String userType){

        if (userService.isUsernameTaken(user.getUsername()) || userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email already in use.");
        }
        try{
            userService.signUpUser(user, userType);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location","/taskflow/sign-in").body("Sign-up successful. Now Sign-in");
        }catch (Exception e){
            String messageError = "user Not registered Successfully ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("/taskflow/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public String showSignInForm(){
        return "Sign-in Form";
    }

    @PostMapping("/taskflow/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> processSignIn(@RequestBody @Valid SignInRequest signInRequest){

        if (signInRequest.getUsernameOrEmail() == null || signInRequest.getPassword() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");


        User userFound = userService.getUserByUsernameOrEmail(signInRequest.getUsernameOrEmail());
        if(userFound == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");


        if (!userService.isPasswordValid(userFound, signInRequest.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");


        return ResponseEntity.status(HttpStatus.FOUND).header("Location","/taskflow/dashboard").body("Sign-in successful");

    }

    @GetMapping("/taskflow/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public String showDashboardPage(){
        return "Dashboard Page";
    }

}
