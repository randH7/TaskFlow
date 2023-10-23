package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_project")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private Integer projectId;

    @NotBlank
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "manger")
    @NotBlank
    private Manger manger;

    @ManyToOne
    @JoinColumn(name = "leader")
    @NotBlank
    private TeamMember leader;

    @OneToMany(mappedBy = "project")
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

}
