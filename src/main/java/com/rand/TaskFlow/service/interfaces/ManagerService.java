package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.ListOfEmployDTO;

import java.util.List;

public interface ManagerService {

    String inviteEmploy(String mangerUsername, String employUsername);

    String removeEmploy(String mangerUsername, String employUsername);

    List<ListOfEmployDTO> getEmployees(String mangerUsername);

}
