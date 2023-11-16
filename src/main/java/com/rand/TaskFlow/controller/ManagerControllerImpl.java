package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.EmployDTO;
import com.rand.TaskFlow.service.implementations.ManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/manager")
public class ManagerControllerImpl {

    @Autowired
    ManagerServiceImpl managerService;

    @PatchMapping("/invite-employ")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> inviteEmploy(@RequestBody EmployDTO employDTO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getName().toString();

        try {
            String message = managerService.inviteEmploy(mangerUsername, employDTO.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Employ Not added Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;
        }

    }

    @PatchMapping("/remove-employ")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> removeEmploy(@RequestBody EmployDTO employDTO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mangerUsername = auth.getName().toString();

        try {
            String message = managerService.removeEmploy(mangerUsername, employDTO.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        }catch (Exception e){
            String messageError = "Employ Not removed Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError) ;
        }

    }

}
