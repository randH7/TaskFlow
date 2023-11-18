package com.rand.TaskFlow.DTO.DetailsTask;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class DetailsTaskEmployeesDTO {

    private DetailsTaskDTO detailsTaskDTO;
    private List<String> employeesUsername;

    public DetailsTaskEmployeesDTO(DetailsTaskDTO detailsTaskDTO, List<String>  employeesUsername) {
        this.detailsTaskDTO = detailsTaskDTO;
        this.employeesUsername = employeesUsername;
    }

}
