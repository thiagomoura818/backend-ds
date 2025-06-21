package com.dsbackend.dsback20233004511.dto;

public class OperacaoDTO {
	private String conta1;
	private String conta2;
	private Double valor;
	
	public OperacaoDTO() {
		
	}

	public OperacaoDTO(String conta1, String conta2, Double valor) {
		super();
		this.conta1 = conta1;
		this.conta2 = conta2;
		this.valor = valor;
	}

	public String getConta1() {
		return conta1;
	}

	public void setConta1(String conta1) {
		this.conta1 = conta1;
	}

	public String getConta2() {
		return conta2;
	}

	public void setConta2(String conta2) {
		this.conta2 = conta2;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
}		
