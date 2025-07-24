package com.dsbackend.dsback20233004511.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsbackend.dsback20233004511.entities.Historico;


@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByClienteId(Long id);
}