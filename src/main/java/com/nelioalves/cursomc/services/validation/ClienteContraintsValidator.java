package com.nelioalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.resources.exceptions.FieldErrorMessage;

public class ClienteContraintsValidator implements ConstraintValidator<ClienteConstraint, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository cliRepo;
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		
		List<FieldErrorMessage> list = new ArrayList<>();
		
		System.out.println("Entrou em ClienteInsertValidator.isValid");
		System.out.println(objDto.getTipoCliente());
		System.out.println(objDto.getCpfCnpj());
		
		// Valida CPF ou CNPJ
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo())) {
			if(!DocumentUtils.isValidCPF(objDto.getCpfCnpj())){
				list.add(new FieldErrorMessage("cpfCnpj", "CPF Inválido"));
			}
		}
		else {
			if(!DocumentUtils.isValidCNPJ(objDto.getCpfCnpj())){
				list.add(new FieldErrorMessage("cpfCnpj", "CNPJ Inválido"));
			}
		}
		
		// Valida email duplicado
		if( cliRepo.findByEmail(objDto.getEmail()) != null) {
			list.add(new FieldErrorMessage("email", "eMail já cadastrado"));
		}
		
		// 	inclua os testes aqui, inserindo erros na lista
		for (FieldErrorMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
					.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
