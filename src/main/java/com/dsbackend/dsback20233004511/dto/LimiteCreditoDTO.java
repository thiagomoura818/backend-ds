package com.dsbackend.dsback20233004511.dto;

public class LimiteCreditoDTO {
	private Double limiteCredito;
	
	public LimiteCreditoDTO() {
		
	}
	
	public LimiteCreditoDTO(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	
	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	
	public Double getLimiteCredito() {
		return this.limiteCredito;
	}
}
