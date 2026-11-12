package co.edu.smartlibrary.service;

import co.edu.smartlibrary.dto.PrestamoRequest;
import co.edu.smartlibrary.dto.PrestamoResponse;
import co.edu.smartlibrary.exception.LibroNoDisponibleException;
import co.edu.smartlibrary.exception.RecursoNoEncontradoException;
import co.edu.smartlibrary.model.Estudiante;
import co.edu.smartlibrary.model.Libro;
import co.edu.smartlibrary.model.Prestamo;
import co.edu.smartlibrary.repository.EstudianteRepository;
import co.edu.smartlibrary.repository.LibroRepository;
import co.edu.smartlibrary.repository.PrestamoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Capa de negocio: coordina el caso de uso "Registrar prestamo" (controlador GRASP)
 * y contiene la regla de disponibilidad.
 */
@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final EstudianteRepository estudianteRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           LibroRepository libroRepository,
                           EstudianteRepository estudianteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * La transaccion garantiza que el cambio de disponibilidad y la creacion
     * del prestamo se confirmen juntos o no se confirme ninguno.
     */
    @Transactional
    public PrestamoResponse registrarPrestamo(PrestamoRequest request) {
        Estudiante estudiante = estudianteRepository.findById(request.estudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el estudiante con id " + request.estudianteId()));

        Libro libro = libroRepository.findById(request.libroId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el libro con id " + request.libroId()));

        // R1: solo un libro disponible puede prestarse
        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException(
                    "El libro '" + libro.getTitulo() + "' ya esta prestado");
        }

        // R2: el prestamo registra estudiante, libro y fecha
        Prestamo prestamo = new Prestamo(null, LocalDate.now(), estudiante, libro);

        // R3: al prestarlo, el libro deja de estar disponible
        libro.marcarPrestado();
        libroRepository.save(libro);

        return PrestamoResponse.desde(prestamoRepository.save(prestamo));
    }

    @Transactional(readOnly = true)
    public List<PrestamoResponse> listarPrestamos() {
        return prestamoRepository.findAllConDetalle().stream()
                .map(PrestamoResponse::desde)
                .toList();
    }
}
