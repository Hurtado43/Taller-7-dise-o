package co.edu.smartlibrary;

import java.io.IOException;
import java.net.ServerSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SmartLibraryApplication {

    /** Puertos que se intentan en orden; se usa el primero que este libre. */
    private static final int[] PUERTOS_CANDIDATOS = {8080, 8081, 8082, 8090, 8888, 9090, 9191, 18080};

    public static void main(String[] args) {
        // Si no se indico un puerto explicito (--server.port o -Dserver.port), se busca uno libre.
        if (!puertoIndicado(args)) {
            System.setProperty("server.port", String.valueOf(buscarPuertoLibre()));
        }

        ConfigurableApplicationContext contexto = SpringApplication.run(SmartLibraryApplication.class, args);

        String puerto = contexto.getEnvironment().getProperty("local.server.port");
        System.out.println();
        System.out.println("==============================================================");
        System.out.println(" SmartLibrary lista en:  http://localhost:" + puerto + "/api/prestamos");
        System.out.println(" Consola H2:             http://localhost:" + puerto + "/h2-console");
        System.out.println(" (Ctrl+C para detenerla)");
        System.out.println("==============================================================");
    }

    private static boolean puertoIndicado(String[] args) {
        if (System.getProperty("server.port") != null || System.getenv("SERVER_PORT") != null) {
            return true;
        }
        for (String arg : args) {
            if (arg.startsWith("--server.port")) {
                return true;
            }
        }
        return false;
    }

    private static int buscarPuertoLibre() {
        for (int puerto : PUERTOS_CANDIDATOS) {
            try (ServerSocket socket = new ServerSocket(puerto)) {
                return puerto;
            } catch (IOException ocupado) {
                // puerto ocupado o reservado por el sistema: se prueba el siguiente
            }
        }
        return 0; // 0 = el sistema operativo asigna cualquier puerto libre
    }
}
