CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    edad INT NOT NULL,
    fecha_nacimiento DATE NOT NULL
);