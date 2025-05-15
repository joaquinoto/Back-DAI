package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "recetas_guardadas")
@Data
public class RecetaGuardada {
    @EmbeddedId
    private RecetaGuardadaId id;

    @Column(name = "fechaGuardado")
    private LocalDateTime fechaGuardado = LocalDateTime.now();
}