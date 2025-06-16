package mx.edu.utez.AplicacionDePrincipios.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
public class CedeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private String estado;

    private String municipio;

    @OneToMany(mappedBy = "cede")
    private List<AlmacenEntity> almacenes;

    @PostPersist      // se ejecuta justo después de insertarse y contar con el id
    private void generarClave() {
        var fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        var random = String.format("%04d", new Random().nextInt(10_000));
        this.clave = "C" + id + "-" + fecha + "-" + random;
    }
}
