package kg.kim.TaskManagementAPI.controller;

import jakarta.validation.Valid;
import kg.kim.TaskManagementAPI.payload.user.UserResponse;
import kg.kim.TaskManagementAPI.payload.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public ResponseEntity<Page<UserResponse>> showAllUsers(@PageableDefault(page = 0, size = 10, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println("Получили всех пользователей");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long userId) {
        System.out.println("Получили пользователя");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long userId,
                                                   @Valid @RequestBody UserUpdateRequest updatedUser) {
        System.out.println("Изменили пользователя");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        System.out.println("Удалили пользователя");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/recover")
    public ResponseEntity<Void> restoreUser(@PathVariable("id") Long userId) {
        System.out.println("Восстановили пользователя");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable("id") Long userId) {
        System.out.println("Заблокировали пользователя");
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
