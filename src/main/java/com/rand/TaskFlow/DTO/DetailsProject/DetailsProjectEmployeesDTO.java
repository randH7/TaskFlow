package com.rand.TaskFlow.DTO.DetailsProject;

import lombok.*;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailsProjectEmployeesDTO {

    private DetailsProjectDTO detailsProjectDTO;
    private List<String> employeesNames;
    private List<String> employeesUsernames;

    public DetailsProjectEmployeesDTO(DetailsProjectDTO detailsProjectDTO, List<String> employeesNames, List<String> employeesUsernames) {
        this.detailsProjectDTO = detailsProjectDTO;
        this.employeesNames = employeesNames;
        this.employeesUsernames = employeesUsernames;
    }

}
