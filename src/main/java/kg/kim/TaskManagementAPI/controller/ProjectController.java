package kg.kim.TaskManagementAPI.controller;

import jakarta.validation.Valid;
import kg.kim.TaskManagementAPI.payload.project.ProjectCreateRequest;
import kg.kim.TaskManagementAPI.payload.project.ProjectCreateResponse;
import kg.kim.TaskManagementAPI.payload.project.ProjectResponseDTO;
import kg.kim.TaskManagementAPI.payload.project.ProjectUpdateRequest;
import kg.kim.TaskManagementAPI.payload.task.TaskResponseDTO;
import kg.kim.TaskManagementAPI.payload.user.UserDTO;
import kg.kim.TaskManagementAPI.payload.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/projects")
public class ProjectController {

    // может Admin
    @GetMapping()
    public ResponseEntity<Page<ProjectResponseDTO>> getAllProjects(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("Получили  все проекты");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // может
    // admin
    // участник этого проекта
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable("id") Long taskId) {
        System.out.println("Получили  проект по id");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // может
    // admin
    // участник этого проекта
    @GetMapping("/{id}/tasks")
    public ResponseEntity<TaskResponseDTO> getProjectAllTasks(@PageableDefault(page = 0, size = 10, sort = "description", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @PathVariable("id") Long projectId) {
        System.out.println("Получили  все таски проекта");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // может менеджер
    @PostMapping
    public ResponseEntity<ProjectCreateResponse> createProject(@Valid @RequestBody ProjectCreateRequest project) {
        System.out.println("Создали новый проект");
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // может менеджер
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable("id") Long projectId,
                                                            @Valid @RequestBody ProjectUpdateRequest updatedProject) {
        System.out.println("Изменили проект");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    // может менеджер
    // может админ
    // может участник проекта
    @GetMapping("/{id}/participants")
    public ResponseEntity<Page<UserResponse>> getProjectParticipants(@PageableDefault(page = 0, size = 10, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     @PathVariable("id") Long projectId) {
        System.out.println("Получили  всех участников проекта ");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // менеджер
    @PutMapping("/{id}/participants")
    public ResponseEntity<Void> changeProjectParticipants(@PathVariable("id") Long projectId,
                                                          @Valid @RequestBody List<UserDTO> participants) {
        System.out.println("Изменили участников ");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    // может менеджер
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable("id") Long projectId) {
        System.out.println("Удалили  проект по id");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
