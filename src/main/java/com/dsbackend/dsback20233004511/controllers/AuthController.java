package com.dsbackend.dsback20233004511.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsbackend.dsback20233004511.dto.JwtAuthenticationDTO;
import com.dsbackend.dsback20233004511.dto.LoginDTO;
import com.dsbackend.dsback20233004511.security.JwtTokenProvider;
import com.dsbackend.dsback20233004511.services.HistoricoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HistoricoService historicoService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticationUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDTO.getLogin(),
						loginDTO.getSenha())
		);
		
		
		String ip = request.getRemoteAddr();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		historicoService.insert(ip, loginDTO.getLogin());
		
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationDTO(jwt));
	}
}
