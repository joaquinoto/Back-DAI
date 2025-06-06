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
            .cors(cors -> cors
                .configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(java.util.List.of("*")); // Cambia "*" por tu frontend si quieres restringir
                    corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(java.util.List.of("*"));
                    return corsConfig;
                })
            )
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (VISITANTE)
                .requestMatchers(
                    "/api/auth/**",
                    "/api/usuarios/login",
                    "/api/usuarios/registro/**",
                    "/api/usuarios/recuperar/**",
                    "/api/usuarios/sugerir-alias",
                    "/api/usuarios/liberar-mail",
                    "/api/usuarios/*/avatar",
                    "/api/recetas/ultimas",
                    "/api/recetas/buscar/**",
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

                .requestMatchers("/api/recetas/aprobar/**").hasRole("ADMIN")

                .requestMatchers(
                    "/api/recetas/**",
                    "/api/calificaciones/**",
                    "/api/fotos/**",
                    "/api/pasos/**",
                    "/api/utilizados/**",
                    "/api/multimedia/**",
                    "/api/conversiones/**",
                    "/api/recetas-guardadas/**"
                ).hasAnyRole("USUARIO", "ALUMNO", "ADMIN")

                .requestMatchers(
                    "/api/alumnos/**",
                    "/api/asistencias/**",
                    "/api/cursos/inscribir",
                    "/api/cursos/baja",
                    "/api/cursos/inscriptos",
                    "/api/cursos/registrar-asistencia",
                    "/api/cursos/historial-asistencia",
                    "/api/cursos/aprobacion",
                    "/api/cronogramas/**"
                ).hasAnyRole("ALUMNO", "ADMIN")

                // Solo ADMIN puede crear admins de desarrollo
                .requestMatchers("/api/usuarios/registro/admin-dev").hasRole("ADMIN")

                // Cualquier otra request requiere autenticación
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}