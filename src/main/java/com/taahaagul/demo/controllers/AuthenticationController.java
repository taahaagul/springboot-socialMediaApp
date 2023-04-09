package com.taahaagul.demo.controllers;

import com.taahaagul.demo.requests.AuthenticationRequest;
import com.taahaagul.demo.requests.RefreshRequest;
import com.taahaagul.demo.requests.RegisterRequest;
import com.taahaagul.demo.responses.AuthenticationResponse;
import com.taahaagul.demo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        /*
        AuthenticationResponse response = service.register(request);
        if (response == null || response.getToken() == null) {
            return (ResponseEntity<AuthenticationResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
        */
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(service.refresh(refreshRequest));
    }
}
