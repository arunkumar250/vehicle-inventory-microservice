package com.vehicleinventorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vehicleinventorysystem.Repository.UserRepository;
import com.vehicleinventorysystem.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @Transactional(readOnly = true)
    public User validateUser(String username, String password) {
        try {
            User user = userRepository.findByUserName(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException("Validation failed for user: " + username, e);
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(int id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.orElse(null);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch user with ID: " + id, e);
        }
    }
    
    @Transactional(readOnly = true)
    public boolean getUserByName(String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            if(user!=null) {
            	return true;
            }
            else {
            	return false;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to fetch user with the Name: " + userName, e);
        }
    }

    @Transactional
    public Map<String, String> saveUser(User user) {
        Map<String, String> response = new HashMap<>();
        try {
            userRepository.save(user);
            response.put("status", "success");
            response.put("message", "User added successfully");
        } catch (DataAccessException e) {
            response.put("status", "error");
            response.put("message", "User not able to be added");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    @Transactional
    public Map<String, String> updateUser(int id, User user) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
                response.put("status", "success");
                response.put("message", "User updated successfully");
            } else {
                response.put("status", "error");
                response.put("message", "User with ID " + id + " not found");
            }
        } catch (DataAccessException e) {
            response.put("status", "error");
            response.put("message", "User not able to be updated");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    @Transactional
    public Map<String, String> deleteUser(int id) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "User with ID " + id + " deleted successfully");
            } else {
                response.put("status", "error");
                response.put("message", "User with ID " + id + " not found");
            }
        } catch (DataAccessException e) {
            response.put("status", "error");
            response.put("message", "User not able to be deleted");
            response.put("reason", e.getMessage());
        }
        return response;
    }
}
