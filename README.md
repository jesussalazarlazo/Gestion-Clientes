
# API para Gestión de Clientes

Este proyecto es una API RESTful desarrollada en **Java** utilizando el framework **Spring Boot**. Su objetivo es gestionar clientes, incluyendo la creación, validación y manejo de datos relacionados.

---

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot** (versión 2.7.x o superior)
  - Spring Web
  - Spring Data JPA
  - Hibernate Validator
- **MySQL** (como base de datos relacional)
- **Maven** (para la gestión de dependencias)
- **Flyway** (para la migración y versionado de la base de datos)
- **Lombok** (para reducir código boilerplate)
- **Hibernate Validator** (para validaciones en los DTOs)

---

## Requisitos previos

1. Tener configurado un entorno de desarrollo con Java 17.
2. Tener instalado MySQL y configurada una base de datos para la aplicación.
3. Configurar las siguientes propiedades en el archivo `application.properties` o `application.yml`:

   ```properties
     spring.application.name=gestor_clientes
    spring.datasource.url=jdbc:mysql://localhost:3306/gestor_clientes
    spring.datasource.username=root
    spring.datasource.password=mysql
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.flyway.enabled=true
    spring.flyway.locations=classpath:db/migration
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=none
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    server.port=8080
    spring.security.user.name=admin
    spring.security.user.password=admin123
   ```

---

## Estructura del proyecto

El proyecto sigue la arquitectura estándar de Spring Boot:

- **Controller**: Maneja las solicitudes HTTP.
- **Service**: Contiene la lógica de negocio.
- **Repository**: Interactúa con la base de datos.
- **DTO (Data Transfer Object)**: Transfiere datos entre las capas.
- **Entity**: Representa las tablas de la base de datos.

---

## Funcionalidades desarrolladas

### 1. Creación de clientes

**Endpoint**: `POST /api/clientes/crear`  
**Descripción**: Permite crear un nuevo cliente en la base de datos.

#### Validaciones incluidas:

- `nombre`: No puede ser nulo ni vacío.
- `apellido`: No puede ser nulo ni vacío.
- `edad`: Debe ser mayor a 0.
- `fechaNacimiento`: Debe estar en el formato `yyyy-MM-dd`.

**Códigos de respuesta**:

- **201**: Cliente creado exitosamente.
- **422**: Error de validación con los detalles de los campos inválidos.

---

### 2. Configuración de validaciones

Se utiliza Hibernate Validator para realizar validaciones automáticas en los DTOs. Ejemplo:

```java
@NotBlank(message = "El nombre no puede estar vacío")
private String nombre;

@NotNull(message = "La edad es obligatoria")
@Min(value = 1, message = "La edad debe ser mayor a 0")
private int edad;
```

Cuando ocurre un error de validación, la API responde con un código **422** y una lista de errores.

---

### 3. Migraciones de base de datos con Flyway

Flyway se utiliza para versionar y migrar la base de datos automáticamente.

#### Ejemplo de archivo de migración

Archivo: `V1__create_clients_table.sql`

```sql
CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    edad INT NOT NULL,
    fecha_nacimiento DATE NOT NULL
);
```

---

## Ejemplo de uso de la API

### Crear un cliente

**Solicitud**:

```http
POST /api/clientes/crear
Content-Type: application/json

{
    "nombre": "Juan",
    "apellido": "Pérez",
    "edad": 30,
    "fechaNacimiento": "1993-01-15"
}
```

**Respuesta exitosa**:

```json
{
    "mensaje": "Usuario creado exitosamente",
    "cliente": {
        "id": 1,
        "nombre": "Juan",
        "apellido": "Pérez",
        "edad": 30,
        "fechaNacimiento": "1993-01-15"
    }
}
```

**Respuesta con errores de validación**:

```json
[
    "El nombre no puede estar vacío",
    "La edad debe ser mayor a 0"
]
```

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/jesussalazarlazo/gestion-clientes.git
   cd gestor-clientes
   ```

2. Construir el proyecto:

   ```bash
   mvn clean install
   ```

3. Ejecutar la aplicación:

   ```bash
   mvn spring-boot:run
   ```

4. La API estará disponible en: [http://localhost:8080](http://localhost:8080)

---

## Próximos pasos

1. Implementar endpoints adicionales para la actualización y eliminación de clientes.
2. Agregar seguridad con Spring Security.
3. Crear una documentación con Swagger para facilitar la prueba de los endpoints.

---

## Autor

**Jesus Salazar Lazo**  
jesussalazarlazo@hotmail.com  

---
