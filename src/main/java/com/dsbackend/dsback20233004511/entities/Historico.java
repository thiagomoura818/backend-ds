package com.dsbackend.dsback20233004511.entities;

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
	private Conta conta;
	@Column(nullable=false)
	private Date dataAcesso;
	@Column(nullable=false)
	private String ipAcesso;
	
	public Historico() {
		
	}

	public Historico(Long id, Conta conta, String ipAcesso) {
		super();
		this.id = id;
		this.conta = conta;
		this.ipAcesso = ipAcesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getIpAcesso() {
		return ipAcesso;
	}

	public void setIpAcesso(String ipAcesso) {
		this.ipAcesso = ipAcesso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(conta, id, ipAcesso);
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
		return Objects.equals(conta, other.conta) && Objects.equals(id, other.id)
				&& Objects.equals(ipAcesso, other.ipAcesso);
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}
	
	
	
}
