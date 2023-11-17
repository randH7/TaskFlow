package com.rand.TaskFlow.DTO.DetailsProject;

import com.rand.TaskFlow.entity.enums.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class DetailsProjectEmployeesDTO {

    private DetailsProjectDTO detailsProjectDTO;
    private List<String> employeesUsername;

    public DetailsProjectEmployeesDTO(DetailsProjectDTO detailsProjectDTO, List<String>  employeesUsername) {
        this.detailsProjectDTO = detailsProjectDTO;
        this.employeesUsername = employeesUsername;
    }

}
