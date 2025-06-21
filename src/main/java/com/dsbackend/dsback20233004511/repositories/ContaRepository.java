package com.dsbackend.dsback20233004511.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsbackend.dsback20233004511.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
	boolean existsByNumero(String numero);
	boolean existsByChavePix(String chavePix);
	Optional<Conta> findByNumero(String numero);
	Optional<Conta> findByChavePix(String chavePix);
	List<Conta> findByClienteId(Long id);
}
