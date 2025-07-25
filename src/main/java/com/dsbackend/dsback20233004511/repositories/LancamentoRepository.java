package com.dsbackend.dsback20233004511.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsbackend.dsback20233004511.entities.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	List<Lancamento> findByContaId(Long id);
}
