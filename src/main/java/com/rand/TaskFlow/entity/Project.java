package com.rand.TaskFlow.entity;

import com.rand.TaskFlow.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "tbl_project")
@Getter @Setter @NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @NotBlank
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "manager")
    @NotNull
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "leader")
    @NotNull
    private Employ leader;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectStatus projectStatus;

    public Project(String projectName, Manager manager, Employ leader, Date startDate, Date dueDate, String description, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.manager = manager;
        this.leader = leader;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus;
    }

}
