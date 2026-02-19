package com.example.amcart.user.service;

import com.example.amcart.user.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    public void login(LoginRequest request){
        UsernamePasswordAuthenticationToken authenticationToken=
                               new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                                        request.getPassword());
        authenticationManager.authenticate(authenticationToken);
    }

}
