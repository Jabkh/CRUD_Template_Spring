package org.example.crud_template.controller;

import org.example.crud_template.dto.BaseResponseDto;
import org.example.crud_template.dto.UserDTO;
import org.example.crud_template.model.User;
import org.example.crud_template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDto<String>> registerUser(@RequestBody User user) {
        boolean isCreated = userService.createUser(user);
        String message = isCreated ? "User registered successfully" : "User registration failed";
        return ResponseEntity.ok(new BaseResponseDto<>(message));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto<String>> loginUser(@RequestBody UserDTO userDto) {
        if (!userService.checkUserNameExists(userDto.getEmail())) {
            return ResponseEntity.status(404).body(new BaseResponseDto<>("User does not exist"));
        }

        boolean isVerified = userService.verifyUser(userDto.getEmail(), userDto.getPassword());
        if (!isVerified) {
            return ResponseEntity.status(401).body(new BaseResponseDto<>("Wrong password"));
        }

        String token = userService.generateToken(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(new BaseResponseDto<>("Success", token));
    }
}
