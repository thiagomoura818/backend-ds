package com.dsbackend.dsback20233004511.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.ClienteDTO;
import com.dsbackend.dsback20233004511.entities.Cliente;
import com.dsbackend.dsback20233004511.repositories.ClienteRepository;
import com.dsbackend.dsback20233004511.security.JwtTokenProvider;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public ClienteDTO findById(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com Id " + id));
		
		return new ClienteDTO(cliente);
	}
	
	public ClienteDTO findByToken(String authHeader) {

		String token = authHeader.replace("Bearer ", "");
		String cpf = jwtTokenProvider.getUsernameFromJWT(token);
		
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(()->
		new EntityNotFoundException("Erro!"));
		
		return new ClienteDTO(cliente);
	}
	
	public List<ClienteDTO> findAll(){
		
		List<Cliente> listaClientes = clienteRepository.findAll();
		return listaClientes.stream().map(ClienteDTO::new).toList();
	}
	
	public ClienteDTO insert(ClienteDTO clienteDTO) {
		if(clienteRepository.existsByCpf(clienteDTO.getCpf())) 
			throw new IllegalArgumentException("Já existe uma cliente com esse cpf "  + clienteDTO.getCpf());
		if(clienteRepository.existsByEmail(clienteDTO.getEmail()))
			throw new IllegalArgumentException("Já existe um cliente com esse email " + clienteDTO.getEmail());
		Cliente cliente = new Cliente();
		cliente.setCpf(clienteDTO.getCpf());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setNome(clienteDTO.getNome());
		cliente.setTelefone(clienteDTO.getTelefone());
		Cliente clienteSalvo = clienteRepository.save(cliente);
		return new ClienteDTO(clienteSalvo);
	}
	
	
}
