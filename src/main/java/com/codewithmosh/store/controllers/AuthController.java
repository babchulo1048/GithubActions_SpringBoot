package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.requests.LoginRequest;
import com.codewithmosh.store.dtos.responses.JwtResponse;
import com.codewithmosh.store.dtos.responses.UserDto;
import com.codewithmosh.store.mappers.UserMapper;
//import com.codewithmosh.store.security.JwtConfig;
import com.codewithmosh.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        if ("bab@gmail.com".equals(request.getEmail()) && "123".equals(request.getPassword())) {
//            return ResponseEntity.ok("Login successful!");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        var loginResult = authService.login(request);

        return new JwtResponse(loginResult.getAccessToken());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        var user = authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}

