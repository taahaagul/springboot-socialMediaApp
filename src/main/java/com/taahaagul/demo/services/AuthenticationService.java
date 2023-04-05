package com.taahaagul.demo.services;

import com.taahaagul.demo.config.JwtService;
import com.taahaagul.demo.entities.Role;
import com.taahaagul.demo.entities.User;
import com.taahaagul.demo.repos.UserRepository;
import com.taahaagul.demo.requests.AuthenticationRequest;
import com.taahaagul.demo.requests.RegisterRequest;
import com.taahaagul.demo.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> existingUser = repository.findByUserName(request.getUserName());
        if (existingUser.isPresent()) {
            return AuthenticationResponse.builder()
                    .token("Username already in use.")
                    .userId(null)
                    .build();
        }

        var user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(), request.getPassword())
        );
        var user = repository.findByUserName(request.getUserName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }
}
