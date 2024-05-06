package kg.kim.TaskManagementAPI.controller;

import jakarta.validation.Valid;
import kg.kim.TaskManagementAPI.enums.TaskStatus;
import kg.kim.TaskManagementAPI.payload.task.TaskCreateRequest;
import kg.kim.TaskManagementAPI.payload.task.TaskCreateResponse;
import kg.kim.TaskManagementAPI.payload.task.TaskResponseDTO;
import kg.kim.TaskManagementAPI.payload.task.TaskUpdateRequest;
import kg.kim.TaskManagementAPI.payload.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @PostMapping
    public ResponseEntity<TaskCreateResponse> createTask(@Valid @RequestBody TaskCreateRequest task) {
        // если пользователь является участником проекта этой задачи
        System.out.println("Создали новую задачу");
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable("id") Long taskId) {
        // если пользователь является участником проекта этой задачи
        System.out.println("Получили  задачу");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> changeTask(@PathVariable("id") Long taskId,
                                                      @Valid @RequestBody TaskUpdateRequest updatedTask) {
        System.out.println("Изменили задачу");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long taskId) {
        System.out.println("Удалили  задачу");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> markAsDone(@PathVariable("id") Long taskId) {
        System.out.println("Пометили задачу как сделано");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")
    @GetMapping("/{id}/status")
    public ResponseEntity<TaskStatus> showTaskStatus(@PathVariable("id") Long taskId) {
        System.out.println("Получили статус задачи");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER', 'USER')")
    @GetMapping("/{id}/implementors")
    public ResponseEntity<List<UserResponse>> getTasksImplementors(@PathVariable("id") Long taskId) {
        System.out.println("Получили  всех исполнителей задачи");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    @PutMapping("/{id}/implementors")
    public ResponseEntity<Void> assignTaskToUser(@PathVariable("id") Long taskId,
                                                 @RequestParam("id") Long userId) {
        // если пользователь самому себе присваивает таску
        System.out.println("Изменили выполнителей задачи");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
