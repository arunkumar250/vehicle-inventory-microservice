package com.vehicleinventorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vehicleinventorysystem.model.User;
import com.vehicleinventorysystem.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getalluser")
    public ResponseEntity<?> getAllUser() {
        try {
            List<User> users = userService.getAllUser();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to fetch users", "reason", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            User user = userService.validateUser(loginUser.getUsername(), loginUser.getPassword());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Login failed", "reason", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User with ID " + id + " not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to fetch user with ID " + id, "reason", e.getMessage()));
        }
    }
    
    @GetMapping("name/{userName}")
    public ResponseEntity<?> getUserByName(@PathVariable String userName) {
        try {
            boolean checkUserNameAvailability = userService.getUserByName(userName);
            if (checkUserNameAvailability) {
                return ResponseEntity.ok(checkUserNameAvailability);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User with the name "+ userName + " not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to fetch user with the Name " + userName, "reason", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Map<String, String> result = userService.saveUser(user);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to create user", "reason", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            Map<String, String> result = userService.updateUser(id, user);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update user with ID " + id, "reason", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            Map<String, String> result = userService.deleteUser(id);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to delete user with ID " + id, "reason", e.getMessage()));
        }
    }
}
