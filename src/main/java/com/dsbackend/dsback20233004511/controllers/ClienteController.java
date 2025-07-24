package com.dsbackend.dsback20233004511.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.ClienteDTO;
import com.dsbackend.dsback20233004511.services.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<ClienteDTO> clientes = clienteService.findAll();
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/listar/token")
	public ResponseEntity<ClienteDTO> findByToken(@RequestHeader("Authorization") String authHeader){
		ClienteDTO cliente = clienteService.findByToken(authHeader);
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Long id){
		ClienteDTO cliente = clienteService.findById(id);
		return ResponseEntity.ok(cliente);
	}
	
	/*
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO){
		ClienteDTO cliente = clienteService.insert(clienteDTO);
		return ResponseEntity.status(201).body(cliente);
	}
	*/
	
}
