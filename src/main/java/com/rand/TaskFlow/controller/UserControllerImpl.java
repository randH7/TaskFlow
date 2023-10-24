package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.entity.*;
import com.rand.TaskFlow.request.SignInRequest;
import com.rand.TaskFlow.service.implementations.UserServiceImpl;
import com.rand.TaskFlow.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

        if (userService.isUsernameTaken(user.getUsername()) || userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email already in use.");
        }
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.signUpUser(user, userType);
            return ResponseEntity.status(HttpStatus.FOUND).body("Sign-up successful. Now Sign-in");
        }catch (Exception e){
            String messageError = "user Not registered Successfully ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> processSignIn(@RequestParam("usernameOrEmail") @Valid String usernameOrEmail, @RequestParam("password") @Valid String password){
        if (usernameOrEmail == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");


        User userFound = userService.getUserByUsernameOrEmail(usernameOrEmail);
        if(userFound == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");


        password = passwordEncoder.encode(password);

        if (!userService.isPasswordValid(userFound, password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");



        return ResponseEntity.status(HttpStatus.OK).body("Sign-in successful");

    }

//    @GetMapping("/sign-in")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity<String> processSignIn(@RequestBody @Valid SignInRequest signInRequest){
//
//        if (signInRequest.getUsernameOrEmail() == null || signInRequest.getPassword() == null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
//
//
//        User userFound = userService.getUserByUsernameOrEmail(signInRequest.getUsernameOrEmail());
//        if(userFound == null)
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//
//        System.out.println("Works");
//
//        signInRequest.setPassword(passwordEncoder.encode(signInRequest.getPassword()));
//
//        if (!userService.isPasswordValid(userFound, signInRequest.getPassword()))
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
//
//
//
//        return ResponseEntity.status(HttpStatus.OK).body("Sign-in successful");
//
//    }

    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public String showDashboardPage(){
        return "Dashboard Page";
    }

}
