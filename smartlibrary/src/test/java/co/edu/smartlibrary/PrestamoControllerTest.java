package co.edu.smartlibrary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.edu.smartlibrary.repository.LibroRepository;
import co.edu.smartlibrary.repository.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

/** Escenarios de la Actividad 6. Cada prueba se revierte al terminar (datos de data.sql intactos). */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PrestamoControllerTest {

    @Autowired MockMvc mvc;
    @Autowired PrestamoRepository prestamoRepository;
    @Autowired LibroRepository libroRepository;

    private ResultActions prestar(String json) throws Exception {
        return mvc.perform(post("/api/prestamos").contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    void prestamoDeLibroDisponible_devuelve201YRegistraPrestamo() throws Exception {
        long antes = prestamoRepository.count();

        prestar("{\"estudianteId\":2,\"libroId\":1}")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.estudianteId").value(2))
                .andExpect(jsonPath("$.libroId").value(1))
                .andExpect(jsonPath("$.fechaPrestamo").isNotEmpty());

        assertThat(prestamoRepository.count()).isEqualTo(antes + 1);
        assertThat(libroRepository.findById(1L).orElseThrow().isDisponible()).isFalse();
    }

    @Test
    void libroYaPrestado_devuelve409SinNuevoPrestamo() throws Exception {
        long antes = prestamoRepository.count();

        prestar("{\"estudianteId\":2,\"libroId\":3}").andExpect(status().isConflict());

        assertThat(prestamoRepository.count()).isEqualTo(antes);
    }

    @Test
    void mismoLibroDosVeces_elSegundoIntentoDevuelve409() throws Exception {
        prestar("{\"estudianteId\":1,\"libroId\":2}").andExpect(status().isCreated());
        prestar("{\"estudianteId\":2,\"libroId\":2}").andExpect(status().isConflict());
    }

    @Test
    void estudianteInexistente_devuelve404() throws Exception {
        long antes = prestamoRepository.count();

        prestar("{\"estudianteId\":999,\"libroId\":1}").andExpect(status().isNotFound());

        assertThat(prestamoRepository.count()).isEqualTo(antes);
        assertThat(libroRepository.findById(1L).orElseThrow().isDisponible()).isTrue();
    }

    @Test
    void libroInexistente_devuelve404() throws Exception {
        prestar("{\"estudianteId\":1,\"libroId\":999}").andExpect(status().isNotFound());
    }

    @Test
    void solicitudInvalida_devuelve400SinCrearPrestamo() throws Exception {
        long antes = prestamoRepository.count();

        prestar("{\"estudianteId\":1}").andExpect(status().isBadRequest());
        prestar("esto no es json").andExpect(status().isBadRequest());

        assertThat(prestamoRepository.count()).isEqualTo(antes);
    }

    @Test
    void consultaDePrestamos_devuelve200ConRegistros() throws Exception {
        mvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].libroTitulo").value("Design Patterns"))
                .andExpect(jsonPath("$[0].estudianteNombre").value("Ana Torres"));
    }
}
