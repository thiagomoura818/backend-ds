package com.dsbackend.dsback20233004511.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsbackend.dsback20233004511.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Boolean existsByCpf(String cpf);
}
