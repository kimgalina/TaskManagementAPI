package kg.kim.TaskManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortDescription;

    @ManyToMany(mappedBy = "userProjects")
    private List<User> participants;

    @OneToMany(mappedBy = "project")
    private List<Task> projectTasks;
}

