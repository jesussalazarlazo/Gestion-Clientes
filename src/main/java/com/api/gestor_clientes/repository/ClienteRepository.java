package com.api.gestor_clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.gestor_clientes.entity.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
