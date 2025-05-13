package com.cocinaapp.controller;

import com.cocinaapp.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String contrasena = request.get("contrasena");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(correo, contrasena));
        UserDetails userDetails = userDetailsService.loadUserByUsername(correo);

        String token = jwtService.generateToken(userDetails.getUsername(), new HashMap<>());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}