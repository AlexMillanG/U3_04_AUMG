package mx.edu.utez.AplicacionDePrincipios.controllers;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.CedeEntity;
import mx.edu.utez.AplicacionDePrincipios.services.CedeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cedes")
@RequiredArgsConstructor
public class CedeController {

    private final CedeService service;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody CedeEntity cede) {
        return service.save(cede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CedeEntity cede) {
        return service.update(id, cede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
