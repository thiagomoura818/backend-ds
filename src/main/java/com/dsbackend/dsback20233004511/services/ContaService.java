package com.dsbackend.dsback20233004511.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.ContaDTO;
import com.dsbackend.dsback20233004511.dto.LancamentoDTO;
import com.dsbackend.dsback20233004511.entities.Cliente;
import com.dsbackend.dsback20233004511.entities.Conta;
import com.dsbackend.dsback20233004511.entities.Estado;
import com.dsbackend.dsback20233004511.entities.Operacao;
import com.dsbackend.dsback20233004511.entities.Tipo;
import com.dsbackend.dsback20233004511.repositories.ClienteRepository;
import com.dsbackend.dsback20233004511.repositories.ContaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	public List<ContaDTO> findAll(){
		List<Conta> listaContas = contaRepository.findAll();
		return listaContas.stream().map(ContaDTO::new).toList();
	}
	
	public ContaDTO findById(Long id) {
		Conta conta = contaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Conta não encontrada"));
		
		return new ContaDTO(conta);
	}
	
	public String gerarNumero() {
		String alfabeto = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		
		SecureRandom random = new SecureRandom();
		StringBuilder senha = new StringBuilder();
		
		for(int i = 0; i < 2; i++) {
			int index = random.nextInt(alfabeto.length());
			senha.append(alfabeto.charAt(index));
		}
		
		for(int i = 0; i < 6; i++) {
			int index = random.nextInt(10);
			senha.append(index);
		}
		
		return senha.toString();
	}
	
	public ContaDTO insert(ContaDTO contaDTO) {
		Cliente cliente = clienteRepository.findById(contaDTO.getClienteId()).orElseThrow(()->new EntityNotFoundException("Não existe um cliente com esse id! "));
		
		Conta conta = new Conta();
		String numero = gerarNumero();
		while(contaRepository.existsByNumero(numero))
			numero = gerarNumero();
		
		conta.setChavePix(contaDTO.getChavePix());
		conta.setCliente(cliente);
		conta.setLimiteSaldo(0.0);
		conta.setSaldo(0.0);
		conta.setNumero(numero);
		
		Conta contaSalva = contaRepository.save(conta);
		return new ContaDTO(contaSalva);
	}
	
	public ContaDTO atualizarLimiteCredito(Long id, Double limiteCredito) {
		Conta conta = contaRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Essa conta não existe "+id));
		
		if(limiteCredito <= conta.getLimiteSaldo())
			throw new IllegalArgumentException("Não é possível diminuir o limite de crédito ");
		
		conta.setLimiteSaldo(limiteCredito);
		Conta contaSalva = contaRepository.save(conta);
		return new ContaDTO(contaSalva);
	}
	
	public ContaDTO cadastrarChavePix(Long id, String pix) {
		Conta conta = contaRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Essa conta não existe "+id));
		if(contaRepository.existsByChavePix(pix))
			throw new IllegalArgumentException("Essa chave pix já está cadastrada no sistema! " + pix);
		
		conta.setChavePix(pix);
		Conta contaSalva = contaRepository.save(conta);
		return new ContaDTO(contaSalva);
	}
	
	public ContaDTO saque(String numero, Double valor) {
		Conta conta = contaRepository.findByNumero(numero)
				.orElseThrow(()-> new EntityNotFoundException("Essa conta não existe"));
		
		if(valor > conta.getLimiteSaldo()+conta.getLimiteSaldo())
			throw new IllegalArgumentException("O valor não pode exceder o limite de credito");
		
		LancamentoDTO lancamentoDTO = new LancamentoDTO();
		
		conta.setSaldo(conta.getSaldo()-valor);
		Conta contaSalva = contaRepository.save(conta);
		lancamentoDTO.setContaId(contaSalva.getId());
		lancamentoDTO.setEstado(Estado.SAIDA);
		lancamentoDTO.setOperacao(Operacao.SAQUE);
		lancamentoDTO.setTipo(Tipo.DEBITO);
		lancamentoDTO.setValor(valor);
		lancamentoService.insert(lancamentoDTO);
		
		return new ContaDTO(contaSalva);
	}
	
	public ContaDTO deposito(String numero, Double valor) {
		Conta conta = contaRepository.findByNumero(numero)
				.orElseThrow(()-> new EntityNotFoundException("Essa conta não existe"));
		
		
		List<Conta> listaDeContas = contaRepository.findByClienteId(conta.getCliente().getId());
		Double soma =0.0, deposito1, deposito2 = 0.0;
		
		for(Conta c : listaDeContas)
			soma+=c.getSaldo();
		
		deposito1 = 1.10*valor + conta.getSaldo();
		deposito2 = valor + conta.getSaldo();
		
		Conta contaSalva;
		
		if(valor > soma) {
			LancamentoDTO lancamentoDTOBonus;
			lancamentoDTOBonus = new LancamentoDTO();
			
			lancamentoDTOBonus.setEstado(Estado.ENTRADA);
			lancamentoDTOBonus.setOperacao(Operacao.BONUS);
			lancamentoDTOBonus.setTipo(Tipo.CREDITO);
			lancamentoDTOBonus.setValor(0.10*valor);
			
			conta.setSaldo(deposito1);
			contaSalva = contaRepository.save(conta);
			lancamentoDTOBonus.setContaId(contaSalva.getId());
			lancamentoService.insert(lancamentoDTOBonus);
		}else { 
			
			conta.setSaldo(deposito2);
			contaSalva = contaRepository.save(conta);
		}
		
		LancamentoDTO lancamentoDTO = new LancamentoDTO();
		lancamentoDTO.setContaId(contaSalva.getId());
		lancamentoDTO.setEstado(Estado.ENTRADA);
		lancamentoDTO.setOperacao(Operacao.DEPOSITO);
		lancamentoDTO.setTipo(Tipo.CREDITO);
		lancamentoDTO.setValor(valor);
		lancamentoService.insert(lancamentoDTO);
		return new ContaDTO(contaSalva);
	}
	
	public void transferencia(String n1, String n2, Double valor) {
		
	}
	
	public void pix(String n1, String n2, Double valor) {
		
	}
}
