package br.com.rollo.rafael.zxchallenge.validation.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto {

	private List<FieldErrorDto> fieldErrors = new ArrayList<>();
	
	public void addFieldError(String field, String message) {
		FieldErrorDto fieldError = new FieldErrorDto(field, message);
		fieldErrors.add(fieldError);
	}
	
	public List<FieldErrorDto> getFieldErrors() {
		return fieldErrors;
	}
}
