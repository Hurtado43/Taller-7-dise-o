package co.edu.smartlibrary.dto;

import co.edu.smartlibrary.model.Prestamo;
import java.time.LocalDate;

/** Objeto de salida: evita exponer las entidades JPA directamente en la respuesta HTTP. */
public record PrestamoResponse(
        Long id,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucion,
        Long estudianteId,
        String estudianteNombre,
        Long libroId,
        String libroTitulo) {

    public static PrestamoResponse desde(Prestamo p) {
        return new PrestamoResponse(
                p.getId(),
                p.getFechaPrestamo(),
                p.getFechaDevolucion(),
                p.getEstudiante().getId(),
                p.getEstudiante().getNombre(),
                p.getLibro().getId(),
                p.getLibro().getTitulo());
    }
}
