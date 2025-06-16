package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.AlmacenEntity;
import mx.edu.utez.AplicacionDePrincipios.models.AlmacenRepository;
import mx.edu.utez.AplicacionDePrincipios.models.CedeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlmacenService {

    private final AlmacenRepository repository;
    private final CedeRepository cedeRepository;

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
        if (almacen.getPrecioVenta() == null || almacen.getPrecioRenta() == null || almacen.getTamano() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Datos incompletos del almacén", false));

        if (almacen.getCede() == null || !cedeRepository.existsById(almacen.getCede().getId()))
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede inválida", false));

        AlmacenEntity saved = repository.save(almacen);
        // clave se genera tras persistir
        saved = repository.save(saved);
        return ResponseEntity.ok(new ApiResponse(saved, "Almacén registrado correctamente", true));
    }

    public ResponseEntity<ApiResponse> update(Long id, AlmacenEntity almacen) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        almacen.setId(id);
        return ResponseEntity.ok(new ApiResponse(repository.save(almacen), "Almacén actualizado correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Almacén eliminado correctamente", true));
    }
}

