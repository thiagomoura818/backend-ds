package com.dsbackend.dsback20233004511.dto;

import java.util.Date;

import com.dsbackend.dsback20233004511.entities.Historico;

public class HistoricoDTO {

	private Long id;
	private Long contaId;
	private Date dataAcesso;
	private String ipAcesso;
	
	public HistoricoDTO() {
		
	}
	
	public HistoricoDTO(Historico historico) {
		this.id = historico.getId();
		this.contaId = historico.getConta().getId();
		this.dataAcesso=historico.getDataAcesso();
		this.ipAcesso=historico.getIpAcesso();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	public String getIpAcesso() {
		return ipAcesso;
	}

	public void setIpAcesso(String ipAcesso) {
		this.ipAcesso = ipAcesso;
	}
	
	
}
