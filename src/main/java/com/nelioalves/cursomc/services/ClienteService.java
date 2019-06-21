package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository repoEnder;
	
	public Cliente find(Integer id){
		
		Optional<Cliente> opt = repo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}	
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		
		cliente.setId(null);
		cliente = repo.save(cliente);
		
		repoEnder.saveAll(cliente.getEnderecos());
		
		return cliente;
		
	}

	public Cliente update(Cliente updCliente) {
		
		// Para verificar se o id não é nulo
		Cliente cliente = find(updCliente.getId());
		
		cliente.setNome(updCliente.getNome());
		cliente.setEmail(updCliente.getEmail());
		
		return repo.save(cliente);
	}

	public void delete(Integer id) {
		
		// Para verificar se o id não é nulo
		find(id);
		try
		{
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível deletar um cliente que possui pedidos");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
}
