package com.dsbackend.dsback20233004511.entities;

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
@Table(name="tb_conta")
public class Conta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String numero;
	private Double saldo;
	@Column(nullable=false)
	private Double limiteSaldo;
	private String chavePix;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	
	public Conta() {
		
	}

	public Conta(Long id, String numero, Double saldo, Double limiteSaldo, String chavePix, Cliente cliente) {
		super();
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
		this.limiteSaldo = limiteSaldo;
		this.chavePix = chavePix;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getLimiteSaldo() {
		return limiteSaldo;
	}

	public void setLimiteSaldo(Double limiteSaldo) {
		this.limiteSaldo = limiteSaldo;
	}

	public String getChavePix() {
		return chavePix;
	}

	public void setChavePix(String chavePix) {
		this.chavePix = chavePix;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chavePix, cliente, id, limiteSaldo, numero, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(chavePix, other.chavePix) && Objects.equals(cliente, other.cliente)
				&& Objects.equals(id, other.id) && Objects.equals(limiteSaldo, other.limiteSaldo)
				&& Objects.equals(numero, other.numero) && Objects.equals(saldo, other.saldo);
	}
	
	
}
