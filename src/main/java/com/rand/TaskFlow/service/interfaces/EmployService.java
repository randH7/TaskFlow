package com.rand.TaskFlow.service.interfaces;

import com.rand.TaskFlow.DTO.ListOfEmployDTO;

import java.util.List;

public interface EmployService {

    List<ListOfEmployDTO> getContributorEmployees(String employUsername);

}
