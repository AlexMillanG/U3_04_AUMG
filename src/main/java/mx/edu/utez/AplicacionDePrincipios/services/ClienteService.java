package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.ClienteEntity;
import mx.edu.utez.AplicacionDePrincipios.models.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<ClienteEntity> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Cliente encontrado", true));
    }

    public ResponseEntity<ApiResponse> save(ClienteEntity cliente) {
        if (cliente.getNombreCompleto() == null || cliente.getCorreoElectronico() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Nombre y correo son obligatorios", false));
        return ResponseEntity.ok(new ApiResponse(repository.save(cliente), "Cliente registrado correctamente", true));
    }

    public ResponseEntity<ApiResponse> update(Long id, ClienteEntity cliente) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        cliente.setId(id);
        return ResponseEntity.ok(new ApiResponse(repository.save(cliente), "Cliente actualizado correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Cliente eliminado correctamente", true));
    }
}
