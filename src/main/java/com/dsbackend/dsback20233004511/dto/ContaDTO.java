package com.dsbackend.dsback20233004511.dto;

import com.dsbackend.dsback20233004511.entities.Conta;

public class ContaDTO {
	
	private Long id;
	private String numero;
	private Double saldo;
	private Double limiteSaldo;
	private String chavePix;
	private Long clienteId;
	
	public ContaDTO() {
		
	}
	
	public ContaDTO(Conta conta) {
		this.id = conta.getId();
		this.numero = conta.getNumero();
		this.saldo = conta.getSaldo();
		this.limiteSaldo = conta.getLimiteSaldo();
		this.chavePix = conta.getChavePix();
		this.clienteId = conta.getCliente().getId();
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

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
	
}
