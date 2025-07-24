package com.dsbackend.dsback20233004511.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.ContaDTO;
import com.dsbackend.dsback20233004511.dto.OperacaoDTO;
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
	
	@GetMapping("/listar/cliente")
	public ResponseEntity<List<ContaDTO>> findByCliente(@RequestHeader("Authorization") String authHeader){
		List<ContaDTO> contas = contaService.findByCliente(authHeader);
		
		return ResponseEntity.ok(contas);
	}
	
	@PostMapping
	public ResponseEntity<ContaDTO> create(@RequestBody ContaDTO contaDTO, @RequestHeader("Authorization") String authHeader){
		ContaDTO conta = contaService.insert(contaDTO, authHeader);
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/atualizarcredito/{id}")
	public ResponseEntity<ContaDTO> atualizarLimiteCredito(@RequestParam Double limiteCredito, @PathVariable Long id){
		ContaDTO conta = contaService.atualizarLimiteCredito(id, limiteCredito);
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/cpix/{id}")
	public ResponseEntity<ContaDTO> cadastrarChavePix(@RequestParam String chavePix, @PathVariable Long id){
		ContaDTO conta = contaService.cadastrarChavePix(id, chavePix);
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/saque")
	public ResponseEntity<ContaDTO> sacar(@RequestBody OperacaoDTO operacaoDTO){
		ContaDTO conta = contaService.saque(operacaoDTO.getConta1(), operacaoDTO.getValor());
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/deposito")
	public ResponseEntity<ContaDTO> depositar(@RequestBody OperacaoDTO operacaoDTO){
		ContaDTO conta = contaService.deposito(operacaoDTO.getConta1(), operacaoDTO.getValor());
		return ResponseEntity.status(201).body(conta);
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<List<ContaDTO>> transferir(@RequestBody OperacaoDTO operacaoDTO){
		List<ContaDTO> contas = contaService.transferencia(operacaoDTO.getConta1(), operacaoDTO.getConta2(), operacaoDTO.getValor());
		return ResponseEntity.status(201).body(contas);
	}
	
	@PostMapping("/pix")
	public ResponseEntity<List<ContaDTO>> pix(@RequestBody OperacaoDTO operacaoDTO){
		List<ContaDTO> contas = contaService.pix(operacaoDTO.getConta1(), operacaoDTO.getConta2(), operacaoDTO.getValor());
		return ResponseEntity.status(201).body(contas);
	}
}
