package mx.edu.utez.AplicacionDePrincipios.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    private String numeroTelefono;

    private String correoElectronico;

    @OneToMany(mappedBy = "cliente")
    private List<AlmacenEntity> almacenes;
}