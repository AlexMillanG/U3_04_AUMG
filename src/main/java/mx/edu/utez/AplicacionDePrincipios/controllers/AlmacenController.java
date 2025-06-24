package mx.edu.utez.AplicacionDePrincipios.controllers;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.AplicacionDePrincipios.config.ApiResponse;
import mx.edu.utez.AplicacionDePrincipios.models.AlmacenEntity;
import mx.edu.utez.AplicacionDePrincipios.services.AlmacenService;
import mx.edu.utez.AplicacionDePrincipios.services.AsignacionClienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/almacenes")
@RequiredArgsConstructor
public class AlmacenController {

    private final AlmacenService service;

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody AlmacenEntity almacen) {
        return service.save(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody AlmacenEntity almacen) {
        return service.update(id, almacen);
    }

    @PutMapping("/almacenes/{almacenId}/vender/{clienteId}")
    public ResponseEntity<ApiResponse> venderAlmacen(@PathVariable Long almacenId, @PathVariable Long clienteId) {
        return service.venderAlmacen(almacenId, clienteId);
    }

    @PutMapping("/almacenes/{almacenId}/rentar/{clienteId}")
    public ResponseEntity<ApiResponse> rentarAlmacen(@PathVariable Long almacenId, @PathVariable Long clienteId) {
        return service.rentarAlmacen(almacenId, clienteId);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
