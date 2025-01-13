package com.api.gestor_clientes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.api.gestor_clientes.dto.ClienteDTO;
import com.api.gestor_clientes.service.ClienteService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    
    
    @PostMapping("/crear")
    public ResponseEntity<Object> crearCliente(@RequestBody @Valid ClienteDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
          
            List<String> errores = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
           
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
        }
        
        ClienteDTO nuevoCliente = clienteService.crearCliente(clienteDTO);
        

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario creado exitosamente");
        response.put("cliente", nuevoCliente);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    
    
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/metricas")
    public ResponseEntity<Map<String, Double>> obtenerMetricas() {
        Map<String, Double> metricas = clienteService.obtenerMetricas();
        return ResponseEntity.ok(metricas);
    }
}