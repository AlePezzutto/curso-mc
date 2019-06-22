package com.nelioalves.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	// Isso é uma convenção do Spring: método iniciado por findBy :: ele cria o método sozinho
	@Transactional(readOnly=true)
	Cliente findByEmail(String email); 
}

