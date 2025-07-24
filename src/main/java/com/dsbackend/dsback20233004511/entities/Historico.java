package com.dsbackend.dsback20233004511.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_historico")
public class Historico {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="id_conta", nullable=false)
	private Cliente cliente;
	@Column(nullable=false)
	private LocalDate dataAcesso;
	@Column(nullable=false)
	private String ipAcesso;
	
	public Historico() {
		
	}

	public Historico(Long id, Cliente cliente, String ipAcesso, LocalDate dataAcesso) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.ipAcesso = ipAcesso;
		this.dataAcesso = dataAcesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getIpAcesso() {
		return ipAcesso;
	}

	public void setIpAcesso(String ipAcesso) {
		this.ipAcesso = ipAcesso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, id, ipAcesso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historico other = (Historico) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(id, other.id)
				&& Objects.equals(ipAcesso, other.ipAcesso);
	}

	public LocalDate getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(LocalDate dataAcesso) {
		this.dataAcesso = dataAcesso;
	}
	
	
	
}
