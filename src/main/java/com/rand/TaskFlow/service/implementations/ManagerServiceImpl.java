package com.rand.TaskFlow.service.implementations;

import com.rand.TaskFlow.entity.Employ;
import com.rand.TaskFlow.repository.EmployRepository;
import com.rand.TaskFlow.repository.ManagerRepository;
import com.rand.TaskFlow.service.interfaces.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployRepository employRepo;

    @Override
    public String inviteEmploy(String mangerUsername, String employUsername) {

        Employ employFound = employRepo.findByUsername(employUsername);
        employFound.setManager(managerRepo.findByUsername(mangerUsername));
        employRepo.save(employFound);

        return "Employ added Successfully";
    }

    @Override
    public String removeEmploy(String mangerUsername, String employUsername) {

        Employ employFound = employRepo.findByUsername(employUsername);
        employFound.setManager(null);
        employRepo.save(employFound);

        return "Employ removed Successfully";
    }

}
