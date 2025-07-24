package com.dsbackend.dsback20233004511.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.HistoricoDTO;
import com.dsbackend.dsback20233004511.entities.Cliente;
import com.dsbackend.dsback20233004511.entities.Historico;
import com.dsbackend.dsback20233004511.repositories.ClienteRepository;
import com.dsbackend.dsback20233004511.repositories.HistoricoRepository;
import com.dsbackend.dsback20233004511.security.JwtTokenProvider;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private ClienteRepository clienteRepository; 
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // Buscar todo o histórico
    public List<HistoricoDTO> findAll() {
        List<Historico> listaHistorico = historicoRepository.findAll();
        return listaHistorico.stream().map(HistoricoDTO::new).toList();
    }

    // Buscar por ID
    public HistoricoDTO findById(Long id) {
        Historico historico = historicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Histórico não encontrado com ID: " + id));
        return new HistoricoDTO(historico);
    }

    // Inserir Histórico
    public HistoricoDTO insert(String ip, String login) {
    	Historico historico = new Historico();
    	historico.setDataAcesso(LocalDate.now());
    	historico.setIpAcesso(ip);
    	
    	Cliente cliente = clienteRepository.findByCpf(login).orElseThrow(() -> new EntityNotFoundException("Cliente não existe"));
    	historico.setCliente(cliente);
    	Historico historicoSalvo = historicoRepository.save(historico);
    	
    	return new HistoricoDTO(historicoSalvo);
    }
    
    public List<HistoricoDTO> findByClienteCpf(String authHeader){
    	String token = authHeader.replace("Bearer ", "");
		String cpf = jwtTokenProvider.getUsernameFromJWT(token);
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(()->new EntityNotFoundException("Não existe um cliente com esse id! "));
		
    	if(!clienteRepository.existsById(cliente.getId()))
    		throw new EntityNotFoundException("Esse cliente não existe!");
    	List<Historico> lista = historicoRepository.findByClienteId(cliente.getId());
    	return lista.stream().map(HistoricoDTO::new).toList();
    }
}