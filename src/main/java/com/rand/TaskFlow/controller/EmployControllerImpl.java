package com.rand.TaskFlow.controller;

import com.rand.TaskFlow.DTO.ListOfEmployDTO;
import com.rand.TaskFlow.service.implementations.EmployServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employ")
public class EmployControllerImpl {

    @Autowired
    private EmployServiceImpl employService;

    @GetMapping("/get-contributor-employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getContributorEmployees() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String employUsername = auth.getName();

        try {
            List<ListOfEmployDTO> listEmployDTO = employService.getContributorEmployees(employUsername);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(listEmployDTO);
        }catch (Exception e){
            String messageError = "Can't retrive contributor employees Successfully.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError + e.getMessage()) ;
        }

    }

}
