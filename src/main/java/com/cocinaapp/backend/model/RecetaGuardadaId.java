package com.cocinaapp.backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class RecetaGuardadaId implements Serializable {
    private int idUsuario;
    private int idReceta;
}