package com.cocinaapp.backend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecetaGuardadaDTO {
    private int idUsuario;
    private int idReceta;
    private LocalDateTime fechaGuardado;
    private Receta receta; // ✅ Incluir la receta completa
    
    // ✅ Constructor necesario para el Service
    public RecetaGuardadaDTO(int idUsuario, int idReceta, LocalDateTime fechaGuardado, Receta receta) {
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
        this.fechaGuardado = fechaGuardado;
        this.receta = receta;
    }
    
    // ✅ Constructor vacío para Lombok
    public RecetaGuardadaDTO() {
    }
}