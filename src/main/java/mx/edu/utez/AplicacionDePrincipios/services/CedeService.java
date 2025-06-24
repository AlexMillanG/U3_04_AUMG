package mx.edu.utez.AplicacionDePrincipios.services;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.CedeEntity;
import mx.edu.utez.AplicacionDePrincipios.models.CedeRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CedeService {

    private final CedeRepository repository;

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<CedeEntity> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Cede encontrada", true));
    }

    public ResponseEntity<ApiResponse> save(CedeEntity cede) {
        if (cede.getEstado() == null || cede.getMunicipio() == null ||
                cede.getEstado().trim().isEmpty() || cede.getMunicipio().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(null, "Estado y municipio son obligatorios", false));
        }

        cede.setEstado(Jsoup.clean(cede.getEstado(), Safelist.none()));
        cede.setMunicipio(Jsoup.clean(cede.getMunicipio(),Safelist.none()));

        CedeEntity saved = repository.save(cede);
        saved = repository.save(saved);
        return ResponseEntity.ok(new ApiResponse(saved, "Cede registrada correctamente", true));
    }


    public ResponseEntity<ApiResponse> update(Long id, CedeEntity cede) {
        Optional<CedeEntity> found = repository.findById(id);

        if (found.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(null, "No se encontró la cede con ID " + id, false));
        }

        if (cede.getEstado() == null || cede.getMunicipio() == null ||
                cede.getEstado().trim().isEmpty() || cede.getMunicipio().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(null, "Estado y municipio son obligatorios", false));
        }

        CedeEntity existing = found.get();

        existing.setEstado(Jsoup.clean(cede.getEstado(), Safelist.none()));
        existing.setMunicipio(Jsoup.clean(cede.getMunicipio(), Safelist.none()));
        existing.setClave(found.get().getClave());

        CedeEntity updated = repository.save(existing);
        return ResponseEntity.ok(new ApiResponse(updated, "Cede actualizada correctamente", true));
    }

}

