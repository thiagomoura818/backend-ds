package com.dsbackend.dsback20233004511.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.HistoricoDTO;
import com.dsbackend.dsback20233004511.services.HistoricoService;

@RestController
@RequestMapping("/historico")
public class HistoricoController {
	
	@Autowired
	private HistoricoService historicoService;
	
	@GetMapping
	public ResponseEntity<List<HistoricoDTO>> findByCliente(@RequestHeader("Authorization") String authHeader){
		List<HistoricoDTO> lista = historicoService.findByClienteCpf(authHeader);
		return ResponseEntity.ok(lista);
	}
}
