package com.rand.TaskFlow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_manger")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Manger extends User {

    @OneToMany(mappedBy = "manger")
    private List<Project> projects;

}
