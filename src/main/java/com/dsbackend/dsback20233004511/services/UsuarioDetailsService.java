package com.dsbackend.dsback20233004511.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.entities.Usuario;
import com.dsbackend.dsback20233004511.repositories.UsuarioRepository;
import com.dsbackend.dsback20233004511.security.UsuarioDetails;

@Service
public class UsuarioDetailsService implements UserDetailsService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UsuarioDetails loadUserByUsername(String login) throws UsernameNotFoundException{
		Usuario usuario = usuarioRepository.findByLogin(login)
				.orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado: "+login));
		
		return new UsuarioDetails(usuario);
	}
	
}
