package com.dsbackend.dsback20233004511.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dsbackend.dsback20233004511.entities.Usuario;

public class UsuarioDetails implements UserDetails{
	private static final long serialVersionUID = 1L;
	private final Usuario usuario;
	
	public UsuarioDetails(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getNivelAcesso().name()));
	}
	
	@Override
	public String getPassword() {return usuario.getSenha();}
	
	@Override
	public String getUsername() {return usuario.getLogin();}
	
	@Override
	public boolean isAccountNonExpired() {return true;}
	
	@Override
	public boolean isAccountNonLocked() {return true;}
	
	@Override
	public boolean isCredentialsNonExpired() {return true;}
	
	@Override
	public boolean isEnabled() {return true;}
}
