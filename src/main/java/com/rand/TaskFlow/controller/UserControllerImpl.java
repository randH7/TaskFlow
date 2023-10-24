package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.entity.User;
import com.rand.TaskFlow.service.implementations.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerImpl {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/taskflow/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> processRegistration(@RequestBody @Valid User user, @RequestParam("userType") String userType, HttpServletRequest request){
        if (userService.isUsernameTaken(user.getUsername()) || userService.isEmailTaken(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email already in use.");
        }
        try{
            userService.signUpUser(user, userType);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location","/taskflow/sign-in").body(null);
        }catch (Exception e){
            String messageError = "user Not registered Successfully";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

    @GetMapping("/taskflow/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public String processSignIn(){
        return "sign in";
    }

}
