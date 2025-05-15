package com.cocinaapp.backend.config;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cocinaapp.backend.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity // Habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

                // Endpoints solo para alumnos
                .requestMatchers(
                    "/api/alumnos/**",
                    "/api/asistencias/**",
                    "/api/cronogramas/**"
                ).hasRole("ALUMNO")

                // Endpoints solo para administradores (si los tienes)
                // .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Cualquier otra request requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}