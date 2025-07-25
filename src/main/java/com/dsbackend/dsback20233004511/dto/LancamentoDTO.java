package com.dsbackend.dsback20233004511.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.dsbackend.dsback20233004511.entities.Estado;
import com.dsbackend.dsback20233004511.entities.Lancamento;
import com.dsbackend.dsback20233004511.entities.Operacao;
import com.dsbackend.dsback20233004511.entities.Tipo;

public class LancamentoDTO {

	private Long id;
	private Double valor;
	private Tipo tipo;
	private Operacao operacao;
	private Long contaId;
	private Estado estado;
	private String data;
	
	public LancamentoDTO() {
		
	}
	
	public LancamentoDTO(Lancamento lancamento) {
		this.id = lancamento.getId();
		this.valor = lancamento.getValor();
		this.tipo = lancamento.getTipo();
		this.operacao = lancamento.getOperacao();
		this.contaId = lancamento.getConta().getId();
		this.estado = lancamento.getEstado();
        this.data = lancamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getData() {
		return this.data;
	}
	
	public void setData(LocalDate data) {
		this.data = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
