package com.dsbackend.dsback20233004511.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.LancamentoDTO;
import com.dsbackend.dsback20233004511.entities.Conta;
import com.dsbackend.dsback20233004511.entities.Lancamento;
import com.dsbackend.dsback20233004511.repositories.ContaRepository;
import com.dsbackend.dsback20233004511.repositories.LancamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LancamentoService {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	public LancamentoDTO findById(Long id) {
		Lancamento lancamento = lancamentoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com Id " + id));
		
		return new LancamentoDTO(lancamento);
	}
	
	public List<LancamentoDTO> findByContaId(Long id){
		List<Lancamento> listaLancamentos = lancamentoRepository.findByContaId(id);
		return listaLancamentos.stream().map(LancamentoDTO::new).toList();
	}
	
	public LancamentoDTO insert(LancamentoDTO lancamentoDTO) {
		Conta conta = contaRepository.findById(lancamentoDTO.getContaId())
				.orElseThrow(()-> new EntityNotFoundException("Essa conta não existe " + lancamentoDTO.getContaId()));
		Lancamento lancamento = new Lancamento();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate data = LocalDate.parse(lancamentoDTO.getData(), formatter);
		lancamento.setConta(conta);
		lancamento.setEstado(lancamentoDTO.getEstado());
		lancamento.setOperacao(lancamentoDTO.getOperacao());
		lancamento.setTipo(lancamentoDTO.getTipo());
		lancamento.setValor(lancamentoDTO.getValor());
		lancamento.setData(data);
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		return new LancamentoDTO(lancamentoSalvo);
	}
	
	
}
