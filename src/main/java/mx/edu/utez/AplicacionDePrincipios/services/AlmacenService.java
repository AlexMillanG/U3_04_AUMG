package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlmacenService {

    private final AlmacenRepository repository;
    private final CedeRepository cedeRepository;
    private final ClienteRepository clienteRepository;

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<AlmacenEntity> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Almacén encontrado", true));
    }

    public ResponseEntity<ApiResponse> save(AlmacenEntity almacen) {
        // Validaciones
        if (almacen.getPrecioVenta() == null || almacen.getPrecioRenta() == null || almacen.getTamano() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Datos incompletos del almacén", false));

        if (almacen.getCede() == null || almacen.getCede().getId() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede inválida", false));

        Optional<CedeEntity> foundCede = cedeRepository.findById(almacen.getCede().getId());

        if (foundCede.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede no encontrada", false));


        almacen.setCede(foundCede.get());

        // Guardar sin clave
        almacen.setClave("");
        AlmacenEntity saved = repository.save(almacen);

        // Generar clave: [clave de la cede]-A[id del almacén]
        String claveGenerada = foundCede.get().getClave() + "-A" + saved.getId();
        saved.setClave(claveGenerada);

        // Guardar con clave
        saved = repository.save(saved);

        return ResponseEntity.ok(new ApiResponse(saved, "Almacén registrado correctamente", true));
    }

    public ResponseEntity<ApiResponse> update(Long id, AlmacenEntity almacen) {
        Optional<AlmacenEntity> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Almacén no encontrado", false));

        if (almacen.getPrecioVenta() == null || almacen.getPrecioRenta() == null || almacen.getTamano() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Datos incompletos del almacén", false));

        if (almacen.getCede() == null || almacen.getCede().getId() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede inválida", false));

        Optional<CedeEntity> foundCede = cedeRepository.findById(almacen.getCede().getId());
        if (foundCede.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede no encontrada", false));

        AlmacenEntity existing = found.get();

        // Se conservan clave y fecha de registro original
        almacen.setId(existing.getId());
        almacen.setClave(existing.getClave());
        almacen.setFechaRegistro(existing.getFechaRegistro());

        // Se asocia la cede persistida
        almacen.setCede(foundCede.get());

        AlmacenEntity updated = repository.save(almacen);
        return ResponseEntity.ok(new ApiResponse(updated, "Almacén actualizado correctamente", true));
    }

    public ResponseEntity<ApiResponse> venderAlmacen(Long almacenId, Long clienteId) {
        return asignarClienteYActualizarDisponibilidad(almacenId, clienteId, Disponibilidad.VENDIDO);
    }

    public ResponseEntity<ApiResponse> rentarAlmacen(Long almacenId, Long clienteId) {
        return asignarClienteYActualizarDisponibilidad(almacenId, clienteId, Disponibilidad.RENTADO);
    }

    private ResponseEntity<ApiResponse> asignarClienteYActualizarDisponibilidad(Long almacenId, Long clienteId, Disponibilidad disponibilidad) {
        Optional<AlmacenEntity> optionalAlmacen = repository.findById(almacenId);
        if (optionalAlmacen.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Almacén no encontrado", false));

        Optional<ClienteEntity> optionalCliente = clienteRepository.findById(clienteId);
        if (optionalCliente.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cliente no encontrado", false));

        AlmacenEntity almacen = optionalAlmacen.get();
        almacen.setCliente(optionalCliente.get());
        almacen.setDisponibilidad(disponibilidad);

        AlmacenEntity updated = repository.save(almacen);
        return ResponseEntity.ok(new ApiResponse(updated, "Almacén " + disponibilidad.name().toLowerCase() + " correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Almacén eliminado correctamente", true));
    }
}

