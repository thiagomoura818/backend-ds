package com.dsbackend.dsback20233004511.services;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.dsbackend.dsback20233004511.security.JwtTokenProvider;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public List<ContaDTO> findAll(){
		List<Conta> listaContas = contaRepository.findAll();
		return listaContas.stream().map(ContaDTO::new).toList();
	}
	
	public ContaDTO findById(Long id) {
		Conta conta = contaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Conta não encontrada"));
		
		return new ContaDTO(conta);
	}
	
	public List<ContaDTO> findByCliente(String authHeader){
		String token = authHeader.replace("Bearer ", "");
		String cpf = jwtTokenProvider.getUsernameFromJWT(token);
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(()->new EntityNotFoundException("Não existe um cliente com esse id! "));
		List<Conta> contas = contaRepository.findByClienteId(cliente.getId());
		
		return contas.stream().map(ContaDTO::new).toList();
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
	
	public ContaDTO insert(ContaDTO contaDTO, String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		String cpf = jwtTokenProvider.getUsernameFromJWT(token);
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(()->new EntityNotFoundException("Não existe um cliente com esse id! "));
		
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
		LocalDate dataAtual = LocalDate.now();
		lancamentoDTO.setData(dataAtual);
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
			LocalDate dataAtual = LocalDate.now();
			lancamentoDTOBonus.setData(dataAtual);
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
		LocalDate dataAtual = LocalDate.now();
		lancamentoDTO.setData(dataAtual);
		lancamentoService.insert(lancamentoDTO);
		return new ContaDTO(contaSalva);
	}
	
	public List<ContaDTO> transferencia(String n1, String n2, Double valor) {
		if(n1.equals(n2))
			throw new IllegalArgumentException("Não é possível transferir para a mesma conta ");
		Conta conta1 = contaRepository.findByNumero(n1)
				.orElseThrow(() -> new EntityNotFoundException("Essa conta não existe "+n1));
		
		Conta conta2 = contaRepository.findByNumero(n2)
				.orElseThrow(() -> new EntityNotFoundException("Essa conta não existe "+n2));
		
		Double valorTotal = valor*1.10;
		if(valorTotal > conta1.getLimiteSaldo() + conta1.getSaldo())
			throw new IllegalArgumentException("Saldo insuficiente");
		
		/* l1 -> Valor original {valor} removido da primeira conta
		 * l2 -> Valor taxa {0.10*valor} removido da primeira conta
		 * l3 -> Valor original {valor} adicionado na segunda conta
		 * */
		
		LocalDate dataAtual = LocalDate.now();

		LancamentoDTO l1, l2, l3;
		l1 = new LancamentoDTO();
		l1.setContaId(conta1.getId());
		l1.setEstado(Estado.SAIDA);
		l1.setOperacao(Operacao.TRANSFERENCIA);
		l1.setTipo(Tipo.DEBITO);
		l1.setValor(valor);
		l1.setData(dataAtual);
		
		l2 = new LancamentoDTO();
		l2.setContaId(conta1.getId());
		l2.setEstado(Estado.SAIDA);
		l2.setOperacao(Operacao.TAXA);
		l2.setTipo(Tipo.DEBITO);
		l2.setValor(0.10*valor);
		l2.setData(dataAtual);

		
		l3 = new LancamentoDTO();
		l3.setContaId(conta2.getId());
		l3.setEstado(Estado.ENTRADA);
		l3.setOperacao(Operacao.TRANSFERENCIA);
		l3.setTipo(Tipo.CREDITO);
		l3.setValor(valor);		
		l3.setData(dataAtual);

		
		conta1.setSaldo(conta1.getSaldo()-valorTotal);
		conta2.setSaldo(conta2.getSaldo()+valor);
	
		List<ContaDTO> listaContas = new ArrayList<>();
		Conta conta1Salva = contaRepository.save(conta1);
		Conta conta2Salva = contaRepository.save(conta2);
		listaContas.add(new ContaDTO(conta1Salva));
		listaContas.add(new ContaDTO(conta2Salva));
		
		lancamentoService.insert(l1);
		lancamentoService.insert(l2);
		lancamentoService.insert(l3);
		return listaContas;
	}
	
	public List<ContaDTO> pix(String n1, String n2, Double valor) {
		if(n1.equals(n2))
			throw new IllegalArgumentException("Não é possível transferir para a mesma conta ");
		Conta conta1 = contaRepository.findByChavePix(n1)
				.orElseThrow(() -> new EntityNotFoundException("Essa conta não existe "+n1));
		
		Conta conta2 = contaRepository.findByChavePix(n2)
				.orElseThrow(() -> new EntityNotFoundException("Essa conta não existe "+n2));
		
		
		if(valor > conta1.getLimiteSaldo() + conta1.getSaldo())
			throw new IllegalArgumentException("Saldo insuficiente");
		
		/* l1 -> Valor original {valor} removido da primeira conta
		 * l3 -> Valor original {valor} adicionado na segunda conta
		 * */
		LocalDate dataAtual = LocalDate.now();
		
		LancamentoDTO l1,l3;
		l1 = new LancamentoDTO();
		l1.setContaId(conta1.getId());
		l1.setEstado(Estado.SAIDA);
		l1.setOperacao(Operacao.PIX);
		l1.setTipo(Tipo.DEBITO);
		l1.setValor(valor);
		l1.setData(dataAtual);
		
		l3 = new LancamentoDTO();
		l3.setContaId(conta2.getId());
		l3.setEstado(Estado.ENTRADA);
		l3.setOperacao(Operacao.PIX);
		l3.setTipo(Tipo.CREDITO);
		l3.setValor(valor);
		l3.setData(dataAtual);
		
		conta1.setSaldo(conta1.getSaldo()-valor);
		conta2.setSaldo(conta2.getSaldo()+valor);
	
		List<ContaDTO> listaContas = new ArrayList<>();
		Conta conta1Salva = contaRepository.save(conta1);
		Conta conta2Salva = contaRepository.save(conta2);
		listaContas.add(new ContaDTO(conta1Salva));
		listaContas.add(new ContaDTO(conta2Salva));
		
		lancamentoService.insert(l1);
		lancamentoService.insert(l3);
		return listaContas;
	}
}
