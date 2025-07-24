package com.dsbackend.dsback20233004511.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.dsbackend.dsback20233004511.entities.Historico;

public class HistoricoDTO {

	private Long id;
	private Long contaId;
	private String dataAcesso;
	private String ipAcesso;
	
	public HistoricoDTO() {
		
	}
	
	public HistoricoDTO(Historico historico) {
		this.id = historico.getId();
		this.contaId = historico.getCliente().getId();
		this.dataAcesso=historico.getDataAcesso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
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

	public String getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(LocalDate dataAcesso) {
		this.dataAcesso = dataAcesso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
	}

	public String getIpAcesso() {
		return ipAcesso;
	}

	public void setIpAcesso(String ipAcesso) {
		this.ipAcesso = ipAcesso;
	}
	
	
}
