package com.rand.TaskFlow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
    @JoinColumn(name = "manger")
    @NotNull
    private Manger manger;

    @ManyToOne
    @JoinColumn(name = "leader")
    @NotNull
    private TeamMember leader;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;

    @NotBlank
    private String description;

    @Enumerated
    @NotNull
    private ProjectStatus projectStatus;

    public Project(String projectName, Manger manger, TeamMember leader, Date startDate, Date dueDate, String description, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.manger = manger;
        this.leader = leader;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.description = description;
        this.projectStatus = projectStatus;
    }

}
