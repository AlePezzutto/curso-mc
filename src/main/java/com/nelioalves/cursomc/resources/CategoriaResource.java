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

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Categoria> insert(@Valid @RequestBody CategoriaDTO catDTO){
		
		Categoria categoria = service.insert(catDTO.toCategoria());
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO catDTO
			                          ,@PathVariable Integer id){
		catDTO.setId(id);
		service.update(catDTO.toCategoria());
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Categoria> delete(@PathVariable Integer id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		List<Categoria> obj = service.findAll();
		List<CategoriaDTO> dto = obj.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage( @RequestParam(name="page", defaultValue="0") Integer page
			                                          , @RequestParam(name="linesPerPage", defaultValue="24") Integer linesPerPage
			                                          , @RequestParam(name="orderBy", defaultValue="nome") String orderBy
			                                          , @RequestParam(name="direction", defaultValue="ASC") String direction) {
		
		Page<Categoria> obj = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> dto = obj.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(dto);
	}
	
}
