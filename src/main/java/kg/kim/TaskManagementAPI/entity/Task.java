package kg.kim.TaskManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "userTasks")
    private List<User> implementors;

    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;
}
