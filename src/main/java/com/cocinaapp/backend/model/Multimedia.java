package com.cocinaapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "multimedia")
@Data
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContenido")
    private Integer idContenido;

    @ManyToOne
    @JoinColumn(name = "idPaso", nullable = false)
    private Paso paso;

    @Column(name = "tipo_contenido", nullable = false, length = 10)
    private String tipoContenido;

    @Column(name = "extension", nullable = true, length = 5)
    private String extension;

    @Column(name = "urlContenido", nullable = false, length = 300)
    private String urlContenido;
}
