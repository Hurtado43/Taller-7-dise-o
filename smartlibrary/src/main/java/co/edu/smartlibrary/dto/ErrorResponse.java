package co.edu.smartlibrary.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime fecha, int estado, String error, String mensaje) {
}
