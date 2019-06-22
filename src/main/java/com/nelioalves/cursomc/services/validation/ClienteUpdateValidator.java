package com.nelioalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.resources.exceptions.FieldErrorMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdateConstraint, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository cliRepo;
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldErrorMessage> list = new ArrayList<>();

		// Busca cliente pelo email
		Cliente cliente = cliRepo.findByEmail(objDto.getEmail());
		
		// Valida email duplicado
		if( cliente != null) {
			
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			
			Integer uriId = Integer.parseInt(map.get("id"));

			if(!cliente.getId().equals(uriId)) {
				list.add(new FieldErrorMessage("email", "eMail já cadastrado"));
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
