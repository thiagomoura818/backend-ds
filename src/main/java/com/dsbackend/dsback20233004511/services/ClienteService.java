package com.dsbackend.dsback20233004511.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsbackend.dsback20233004511.dto.ClienteDTO;
import com.dsbackend.dsback20233004511.entities.Cliente;
import com.dsbackend.dsback20233004511.repositories.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public ClienteDTO findById(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado com Id " + id));
		
		return new ClienteDTO(cliente);
	}
	
	public List<ClienteDTO> findAll(){
		List<Cliente> listaClientes = clienteRepository.findAll();
		return listaClientes.stream().map(ClienteDTO::new).toList();
	}
	
	public ClienteDTO insert(ClienteDTO clienteDTO) {
		if(clienteRepository.existsByCpf(clienteDTO.getCpf())) 
			throw new IllegalArgumentException("Já existe uma cliente com esse cpf "  + clienteDTO.getCpf());
		
		Cliente cliente = new Cliente();
		cliente.setCpf(clienteDTO.getCpf());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setNome(clienteDTO.getNome());
		cliente.setTelefone(clienteDTO.getTelefone());
		Cliente clienteSalvo = clienteRepository.save(cliente);
		return new ClienteDTO(clienteSalvo);
	}
	
	
}
