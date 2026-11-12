package co.edu.smartlibrary.controller;

import co.edu.smartlibrary.dto.PrestamoRequest;
import co.edu.smartlibrary.dto.PrestamoResponse;
import co.edu.smartlibrary.service.PrestamoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Capa de presentacion: solo entrada/salida HTTP. No contiene reglas de negocio ni accede a repositorios. */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<PrestamoResponse> registrar(@Valid @RequestBody PrestamoRequest request) {
        PrestamoResponse creado = prestamoService.registrarPrestamo(request);
        return ResponseEntity.created(URI.create("/api/prestamos/" + creado.id())).body(creado);
    }

    @GetMapping
    public List<PrestamoResponse> listar() {
        return prestamoService.listarPrestamos();
    }
}
