package com.dsbackend.dsback20233004511.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String login;
	
	@Column(nullable=false)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private NivelAcesso nivelAcesso;
	
	public Usuario() {
		
	}

	public Usuario(String login, String senha, NivelAcesso nivelAcesso) {
		super();
		this.login = login;
		this.senha = senha;
		this.nivelAcesso = nivelAcesso;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public NivelAcesso getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(NivelAcesso nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
	
	
}
