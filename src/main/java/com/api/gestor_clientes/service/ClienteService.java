package com.api.gestor_clientes.service;

import org.springframework.stereotype.Service;

import com.api.gestor_clientes.dto.ClienteDTO;
import com.api.gestor_clientes.entity.Cliente;
import com.api.gestor_clientes.repository.ClienteRepository;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente); 
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public Map<String, Double> obtenerMetricas() {
        List<Cliente> clientes = clienteRepository.findAll();
        double promedio = clientes.stream().mapToInt(Cliente::getEdad).average().orElse(0.0);
        double desviacion = Math.sqrt(clientes.stream()
                .mapToDouble(cliente -> Math.pow(cliente.getEdad() - promedio, 2))
                .average().orElse(0.0));
        Map<String, Double> metricas = new HashMap<>();
        metricas.put("promedioEdad", promedio);
        metricas.put("desviacionEstandarEdad", desviacion);
        return metricas;
    }
}