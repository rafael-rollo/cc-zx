package br.com.rollo.rafael.zxchallenge.validation.dto;

public class FieldErrorDto {
	
	private String field;
	private String message;

	public FieldErrorDto(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}
	
	public String getMessage() {
		return message;
	}
}
