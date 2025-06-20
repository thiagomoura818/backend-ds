package com.dsbackend.dsback20233004511.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.ChavePix;
import com.dsbackend.dsback20233004511.dto.ContaDTO;
import com.dsbackend.dsback20233004511.dto.LimiteCreditoDTO;
import com.dsbackend.dsback20233004511.services.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ContaDTO>> findAll(){
		List<ContaDTO> contas = contaService.findAll();
		return ResponseEntity.ok(contas);
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<ContaDTO> findById(@PathVariable Long id){
		ContaDTO conta = contaService.findById(id);
		return ResponseEntity.ok(conta);
	}
	
	@PostMapping
	public ResponseEntity<ContaDTO> create(@RequestBody ContaDTO contaDTO){
		ContaDTO conta = contaService.insert(contaDTO);
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/atualizarcredito/{id}")
	public ResponseEntity<ContaDTO> atualizarLimiteCredito(@RequestBody LimiteCreditoDTO limiteCreditoDTO, @PathVariable Long id){
		ContaDTO conta = contaService.atualizarLimiteCredito(id, limiteCreditoDTO.getLimiteCredito());
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/cpix/{id}")
	public ResponseEntity<ContaDTO> cadastrarChavePix(@RequestBody ChavePix chavePix, @PathVariable Long id){
		ContaDTO conta = contaService.cadastrarChavePix(id, chavePix.getChavePix());
		return ResponseEntity.status(201).body(conta);
	}
	
}
