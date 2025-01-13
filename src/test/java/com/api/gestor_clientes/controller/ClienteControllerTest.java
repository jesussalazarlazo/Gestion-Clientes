package com.api.gestor_clientes.controller;

import com.api.gestor_clientes.dto.ClienteDTO;
import com.api.gestor_clientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCliente() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO(null, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));
        ClienteDTO clienteCreado = new ClienteDTO(1L, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));

        
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);  

        when(clienteService.crearCliente(any(ClienteDTO.class))).thenReturn(clienteCreado);

        
        ResponseEntity<Object> response = clienteController.crearCliente(clienteDTO, result);

      
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Usuario creado exitosamente", responseBody.get("mensaje"));
        assertEquals(clienteCreado, responseBody.get("cliente"));
    }
    @Test
    void testCrearClienteConErroresDeValidacion() {
        
        ClienteDTO clienteDTO = new ClienteDTO(null, "", "Perez", 30, LocalDate.of(1993, 1, 15));
        BindingResult result = mock(BindingResult.class);

        when(result.hasErrors()).thenReturn(true);
        when(result.getAllErrors()).thenReturn(List.of(
                new org.springframework.validation.FieldError("clienteDTO", "nombre", "El nombre no puede estar vacío")
        ));

       
        ResponseEntity<Object> response = clienteController.crearCliente(clienteDTO, result);

        
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        List<String> errores = (List<String>) response.getBody();
        assertNotNull(errores);
        assertTrue(errores.contains("El nombre no puede estar vacío"));
    }

    @Test
    void testListarClientes() {
       
        ClienteDTO cliente1 = new ClienteDTO(1L, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));
        ClienteDTO cliente2 = new ClienteDTO(2L, "Ana", "Gomez", 25, LocalDate.of(1998, 6, 10));

        when(clienteService.listarClientes()).thenReturn(Arrays.asList(cliente1, cliente2));

      
        ResponseEntity<List<ClienteDTO>> response = clienteController.listarClientes();

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ClienteDTO> clientes = response.getBody();
        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        assertEquals(cliente1, clientes.get(0));
        assertEquals(cliente2, clientes.get(1));
    }

    @Test
    void testObtenerMetricas() {
        // Arrange
        Map<String, Double> metricas = new HashMap<>();
        metricas.put("promedioEdad", 27.5);
        metricas.put("desviacionEstandarEdad", 2.5);

        when(clienteService.obtenerMetricas()).thenReturn(metricas);

        // Act
        ResponseEntity<Map<String, Double>> response = clienteController.obtenerMetricas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Double> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(27.5, responseBody.get("promedioEdad"));
        assertEquals(2.5, responseBody.get("desviacionEstandarEdad"));
    }
}