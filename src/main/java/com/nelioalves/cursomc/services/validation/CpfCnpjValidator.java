package com.nelioalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.resources.exceptions.FieldErrorMessage;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpjConstraint, ClienteNewDTO> {
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldErrorMessage> list = new ArrayList<>();
		
		System.out.println("Entrou em ClienteInsertValidator.isValid");
		System.out.println(objDto.getTipoCliente());
		System.out.println(objDto.getCpfCnpj());
		
		
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
		
		// 	inclua os testes aqui, inserindo erros na lista
		for (FieldErrorMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
					.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
