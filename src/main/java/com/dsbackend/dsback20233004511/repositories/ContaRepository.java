package com.dsbackend.dsback20233004511.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsbackend.dsback20233004511.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
	boolean existsByNumero(String numero);
	boolean existsByChavePix(String chavePix);
}
