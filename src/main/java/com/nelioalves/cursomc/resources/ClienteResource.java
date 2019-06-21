package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO newCliente){
		
		Cliente cliente = service.insert(newCliente.toCliente());
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO cliDTO
			                          ,@PathVariable Integer id){
		cliDTO.setId(id);
		service.update(cliDTO.toCliente());
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() {
		
		List<Cliente> obj = service.findAll();
		List<ClienteDTO> dto = obj.stream().map(cat -> new ClienteDTO(cat)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<ClienteDTO>> findPage( @RequestParam(name="page", defaultValue="0") Integer page
			                                        , @RequestParam(name="linesPerPage", defaultValue="24") Integer linesPerPage
			                                        , @RequestParam(name="orderBy", defaultValue="nome") String orderBy
			                                        , @RequestParam(name="direction", defaultValue="ASC") String direction) {
		
		Page<Cliente> obj = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> dto = obj.map(cat -> new ClienteDTO(cat));
		return ResponseEntity.ok().body(dto);
	}
	
	
}
