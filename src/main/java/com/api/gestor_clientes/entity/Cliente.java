package com.api.gestor_clientes.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente {

    public Cliente(String string, String string2, int i, LocalDate localDate) {
		
	}

	public Cliente(String string, String string2, int i, String string3) {
		
	}

	@Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

	@NotEmpty(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable   = false)
    private int edad;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
}
