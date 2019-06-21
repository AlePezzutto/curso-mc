package com.nelioalves.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, 
			                                            HttpServletRequest request ) {
		
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), 
				                              e.getMessage(), 
				                              System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, 
			                                            HttpServletRequest request ) {
		
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), 
				                              e.getMessage(), 
				                              System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException e, 
			                                            HttpServletRequest request ) {
		
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value() 
				                                 ,"Erro de validação de campos" 
				                                 ,System.currentTimeMillis());
		
		// Atribui os erros
		for(FieldError fieldErr : e.getBindingResult().getFieldErrors()) {

			err.addError(new FieldErrorMessage(fieldErr.getField(), fieldErr.getDefaultMessage()));
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
		
	}

}
