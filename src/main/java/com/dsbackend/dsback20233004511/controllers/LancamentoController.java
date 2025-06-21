package com.dsbackend.dsback20233004511.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.LancamentoDTO;
import com.dsbackend.dsback20233004511.services.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<LancamentoDTO> findById(@PathVariable Long id){
		LancamentoDTO lancamento = lancamentoService.findById(id);
		return ResponseEntity.ok(lancamento);
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<List<LancamentoDTO>> findAllById(@PathVariable Long id){
		List<LancamentoDTO> lancamentos = lancamentoService.findByContaId(id);
		return ResponseEntity.ok(lancamentos);
	}
}
