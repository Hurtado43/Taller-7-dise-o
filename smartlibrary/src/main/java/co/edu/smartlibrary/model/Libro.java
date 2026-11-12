package co.edu.smartlibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private boolean disponible;

    protected Libro() {
        // requerido por JPA
    }

    public Libro(Long id, String titulo, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.disponible = disponible;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean valor) { this.disponible = valor; }

    /** R3: al prestarlo, el libro deja de estar disponible. */
    public void marcarPrestado() {
        this.disponible = false;
    }
}
