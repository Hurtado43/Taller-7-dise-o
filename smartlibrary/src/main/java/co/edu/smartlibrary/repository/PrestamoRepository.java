package co.edu.smartlibrary.repository;

import co.edu.smartlibrary.model.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    /** Consulta de prestamos con estudiante y libro cargados en una sola consulta. */
    @Query("select p from Prestamo p join fetch p.estudiante join fetch p.libro order by p.id")
    List<Prestamo> findAllConDetalle();
}
