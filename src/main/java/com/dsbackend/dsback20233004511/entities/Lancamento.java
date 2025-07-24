package com.dsbackend.dsback20233004511.entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private Double valor;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo", nullable=false)
	private Tipo tipo;
	@Column(name="operacao", nullable=false)
	private Operacao operacao;
	
	@ManyToOne
	@JoinColumn(name="id_conta", nullable=false)
	private Conta conta;
	
	private LocalDate data;
	
	/*
	 * Tipo enumerado pra representar SAIDA ou ENTRADA de lancamentos na conta
	 * */
	@Enumerated(EnumType.STRING)
	@Column(name="estado", nullable=false)
	private Estado estado;
	
	public Lancamento() {

	}
	
	public Lancamento(Long id, Double valor, Tipo tipo, Operacao operacao, Conta conta, Estado estado, LocalDate data) {
		super();
		this.id = id;
		this.valor = valor;
		this.tipo = tipo;
		this.operacao = operacao;
		this.conta = conta;
		this.estado = estado;
		this.data = data;
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

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(conta, estado, id, operacao, tipo, valor);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(conta, other.conta) && estado == other.estado && Objects.equals(id, other.id)
				&& operacao == other.operacao && tipo == other.tipo && Objects.equals(valor, other.valor);
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	
	
	
}
