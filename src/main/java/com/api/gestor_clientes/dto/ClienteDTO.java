package com.api.gestor_clientes.dto;

import java.time.LocalDate;

import com.api.gestor_clientes.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class ClienteDTO {
    
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @NotEmpty(message = "El apellido no puede estar vacío")
    @NotNull(message = "El apellido es obligatorio")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;

    @Min(value = 1, message = "La Edad debe ser Mayor a 0")
    private int edad;
    
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;
    
   
    public ClienteDTO(Cliente cliente) {
        if (cliente != null) {
            this.id = cliente.getId();  
            this.nombre = cliente.getNombre();
            this.apellido = cliente.getApellido();
            this.edad = cliente.getEdad();
            this.fechaNacimiento = cliente.getFechaNacimiento();
        }
    }

    @JsonCreator
    public ClienteDTO(
            @JsonProperty("nombre") String nombre, 
            @JsonProperty("apellido") String apellido,
            @JsonProperty("edad") int edad, 
            @JsonProperty("fechaNacimiento") String fechaNacimientoStr) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.fechaNacimiento = LocalDate.parse(fechaNacimientoStr); 
    }

    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNombre(this.nombre);
        cliente.setApellido(this.apellido);
        cliente.setEdad(this.edad);
        cliente.setFechaNacimiento(this.fechaNacimiento);
        return cliente;
    }
}