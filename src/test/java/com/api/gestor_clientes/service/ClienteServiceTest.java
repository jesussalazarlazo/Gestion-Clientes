package com.api.gestor_clientes.service;

import com.api.gestor_clientes.dto.ClienteDTO;
import com.api.gestor_clientes.entity.Cliente;
import com.api.gestor_clientes.repository.ClienteRepository;
import com.api.gestor_clientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteDTO = new ClienteDTO(1L, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));
    }

    @Test
    void testCrearCliente() {
     
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

       
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

      
        ClienteDTO result = clienteService.crearCliente(clienteDTO);

      
        assertNotNull(result);
        assertEquals(clienteDTO.getNombre(), result.getNombre());
        assertEquals(clienteDTO.getApellido(), result.getApellido());
        assertEquals(clienteDTO.getEdad(), result.getEdad());
    }

    @Test
    void testListarClientes() {
       
        Cliente cliente1 = new Cliente(1L, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));
        Cliente cliente2 = new Cliente(2L, "Maria", "Lopez", 25, LocalDate.of(1998, 5, 10));
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

       
        when(clienteRepository.findAll()).thenReturn(clientes);

     
        List<ClienteDTO> result = clienteService.listarClientes();

        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cliente1.getNombre(), result.get(0).getNombre());
        assertEquals(cliente2.getApellido(), result.get(1).getApellido());
    }

    @Test
    void testObtenerMetricas() {
       
        Cliente cliente1 = new Cliente(1L, "Juan", "Perez", 30, LocalDate.of(1993, 1, 15));
        Cliente cliente2 = new Cliente(2L, "Maria", "Lopez", 25, LocalDate.of(1998, 5, 10));
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        
        when(clienteRepository.findAll()).thenReturn(clientes);

       
        Map<String, Double> result = clienteService.obtenerMetricas();

        
        assertNotNull(result);
        assertTrue(result.containsKey("promedioEdad"));
        assertTrue(result.containsKey("desviacionEstandarEdad"));
        assertEquals(27.5, result.get("promedioEdad"));
        assertEquals(3.5355339059327378, result.get("desviacionEstandarEdad"));
    }
}