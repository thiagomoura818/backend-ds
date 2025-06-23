package com.dsbackend.dsback20233004511.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.ClienteDTO;
import com.dsbackend.dsback20233004511.entities.NivelAcesso;
import com.dsbackend.dsback20233004511.entities.Usuario;
import com.dsbackend.dsback20233004511.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private ClienteService clienteService;
	
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository=usuarioRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public Usuario criarUsuario(ClienteDTO clienteDTO) {
		if(usuarioRepository.existsByLogin(clienteDTO.getCpf()))
			throw new IllegalArgumentException("Login já existe");
		
		Usuario usuario = new Usuario();
		usuario.setLogin(clienteDTO.getCpf());
		usuario.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
		usuario.setNivelAcesso(NivelAcesso.USUARIO);
		
		clienteService.insert(clienteDTO);
		
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarUsuarios(){
		return usuarioRepository.findAll();
	}
}
