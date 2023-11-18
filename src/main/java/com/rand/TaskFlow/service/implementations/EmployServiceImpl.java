package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.DTO.ListOfEmployDTO;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.service.interfaces.EmployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployServiceImpl implements EmployService {

    @Autowired
    private EmployRepository employRepo;

    @Override
    public List<ListOfEmployDTO> getContributorEmployees(String employUsername) {
        return employRepo.findContributorEmployees(employUsername);
    }

}
