package kg.kim.TaskManagementAPI.controller;

import jakarta.validation.Valid;
import kg.kim.TaskManagementAPI.entity.User;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Page<ProjectResponseDTO>> getAllProjects(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("Получили  все проекты");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable("id") Long taskId) {
        System.out.println("Получили  проект по id");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @GetMapping("/{id}/tasks")
    public ResponseEntity<TaskResponseDTO> getProjectAllTasks(@PageableDefault(page = 0, size = 10, sort = "description", direction = Sort.Direction.DESC) Pageable pageable,
                                                              @PathVariable("id") Long projectId,
                                                              @AuthenticationPrincipal User currentUser) {
        // если пользователь является участником проекта
        System.out.println("Получили  все таски проекта");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ProjectCreateResponse> createProject(@Valid @RequestBody ProjectCreateRequest project) {
        System.out.println("Создали новый проект");
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable("id") Long projectId,
                                                            @Valid @RequestBody ProjectUpdateRequest updatedProject) {
        System.out.println("Изменили проект");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/participants")
    public ResponseEntity<Page<UserResponse>> getProjectParticipants(@PageableDefault(page = 0, size = 10, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable,
                                                                     @PathVariable("id") Long projectId) {
        System.out.println("Получили  всех участников проекта ");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/participants")
    public ResponseEntity<Void> changeProjectParticipants(@PathVariable("id") Long projectId,
                                                          @Valid @RequestBody List<UserDTO> participants) {
        System.out.println("Изменили участников ");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable("id") Long projectId) {
        System.out.println("Удалили  проект по id");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
