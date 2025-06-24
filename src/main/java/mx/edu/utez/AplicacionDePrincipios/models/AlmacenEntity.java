package mx.edu.utez.AplicacionDePrincipios.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class AlmacenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private LocalDate fechaRegistro;

    private BigDecimal precioVenta;

    private BigDecimal precioRenta;

    @Enumerated(EnumType.STRING)
    private Tamano tamano;

    @Enumerated(EnumType.STRING)
    private Disponibilidad disponibilidad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cede_id")
    private CedeEntity cede;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @PrePersist
    private void inicializarFecha() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }

}