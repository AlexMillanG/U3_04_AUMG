package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.AplicacionDePrincipios.models.Disponibilidad;

@Getter
@Setter
public class AsignacionClienteDto {
    private Long clienteId;
    private Disponibilidad disponibilidad;
}
