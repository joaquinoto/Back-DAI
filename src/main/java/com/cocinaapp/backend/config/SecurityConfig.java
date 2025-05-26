package com.cocinaapp.backend.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.cocinaapp.backend.security.JwtAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (visitantes)
                .requestMatchers(
                    "/api/auth/**",
                    "/api/usuarios/login",
                    "/api/usuarios/registro/**",
                    "/api/recetas/ultimas",
                    "/api/recetas/buscar/**",
                    "/api/recetas/{id}",
                    "/api/recetas",
                    "/api/cursos",
                    "/api/cursos/{id}",
                    "/api/tiposReceta",
                    "/api/tiposReceta/{id}",
                    "/api/ingredientes",
                    "/api/ingredientes/{id}",
                    "/api/unidades",
                    "/api/unidades/{id}",
                    "/api/sedes",
                    "/api/sedes/{id}"
                ).permitAll()
                // Permitir aprobar recetas antes que la regla general de recetas
                .requestMatchers("/api/recetas/aprobar/**").hasRole("ADMIN")
                // Endpoints solo para usuarios autenticados (USUARIO o ALUMNO)
                .requestMatchers(
                    "/api/recetas/**",
                    "/api/calificaciones/**",
                    "/api/fotos/**",
                    "/api/pasos/**",
                    "/api/utilizados/**",
                    "/api/multimedia/**",
                    "/api/conversiones/**"
                ).hasAnyRole("USUARIO", "ALUMNO")
                .requestMatchers(
                    "/api/alumnos/**",
                    "/api/asistencias/**",
                    "/api/cronogramas/**"
                ).hasRole("ALUMNO")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}