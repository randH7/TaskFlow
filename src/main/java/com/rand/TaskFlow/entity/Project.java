package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_project")
@Getter @Setter @NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private Integer projectId;

    @NotBlank
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "username")
    @NotBlank
    private Manger manger;

    @ManyToOne
    @JoinColumn(name = "username")
    @NotBlank
    private Leader leader;

    @OneToMany(mappedBy = "tbl_project")
    @NotBlank
    private List<Task> tasks;

    @NotBlank
    @FutureOrPresent
    private Date startDate;

    @NotBlank
    @FutureOrPresent
    private Date dueDate;

    @NotBlank
    private String description;

    @Enumerated
    @NotBlank
    private ProjectStatus projectStatus;

    public Project(String projectName, Manger manger, Leader leader, Date startDate, Date dueDate, String description, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.manger = manger;
        this.leader = leader;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus;
    }
}
