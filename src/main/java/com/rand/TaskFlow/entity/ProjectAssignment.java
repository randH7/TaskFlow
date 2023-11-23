package com.rand.TaskFlow.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;




@Entity
@Table(name = "tbl_project_assignment")
@Getter @Setter @NoArgsConstructor
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectAssignmentId;

    @ManyToOne
    @JoinColumn(name = "username")
    private Employ employ;

    @ManyToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(value = CONSTRAINT, foreignKeyDefinition = "FOREIGN KEY (project_id) REFERENCES tbl_project(project_id) ON DELETE CASCADE"))
    private Project project;

    public ProjectAssignment(Employ employ, Project project) {
        this.employ = employ;
        this.project = project;
    }

}
