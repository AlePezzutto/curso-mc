package com.nelioalves.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldErrorMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldErrorMessage> getErrorList(){
		return this.errors;
	}
	
	public void setErrorList(List<FieldErrorMessage> errors){
		this.errors = errors;
	}
	
	public void addError(FieldErrorMessage error){
		this.errors.add(error);
	}
}
