package co.edu.smartlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaPrestamo;

    /** [0..1] Es null mientras el prestamo esta activo. */
    private LocalDate fechaDevolucion;

    /** Cada prestamo se asocia con exactamente un estudiante (1 a 0..*). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    /** Cada prestamo se asocia con exactamente un libro (1 a 0..*). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "libro_id")
    private Libro libro;

    protected Prestamo() {
        // requerido por JPA
    }

    public Prestamo(Long id, LocalDate fecha, Estudiante estudiante, Libro libro) {
        this.id = id;
        this.fechaPrestamo = fecha;
        this.estudiante = estudiante;
        this.libro = libro;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fecha) { this.fechaPrestamo = fecha; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fecha) { this.fechaDevolucion = fecha; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }
}
