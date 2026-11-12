-- Datos iniciales de prueba (el taller supone estudiantes y libros ya registrados)
INSERT INTO estudiante (nombre, correo) VALUES ('Ana Torres', 'ana.torres@universidad.edu');
INSERT INTO estudiante (nombre, correo) VALUES ('Luis Perez', 'luis.perez@universidad.edu');
INSERT INTO estudiante (nombre, correo) VALUES ('Maria Gomez', 'maria.gomez@universidad.edu');

INSERT INTO libro (titulo, disponible) VALUES ('Clean Code', TRUE);
INSERT INTO libro (titulo, disponible) VALUES ('UML Distilled', TRUE);
INSERT INTO libro (titulo, disponible) VALUES ('Design Patterns', FALSE);
INSERT INTO libro (titulo, disponible) VALUES ('Cien anios de soledad', TRUE);

-- El libro 3 ya esta prestado a la estudiante 1 (prestamo activo: sin fecha de devolucion)
INSERT INTO prestamo (fecha_prestamo, fecha_devolucion, estudiante_id, libro_id) VALUES (DATE '2026-11-01', NULL, 1, 3);
