package br.com.rollo.rafael.zxchallenge.validation.dto;

public class FieldErrorDto {
	
	private String field;
	private String message;

	FieldErrorDto() {
	}
	
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldErrorDto other = (FieldErrorDto) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FieldErrorDto [field=" + field + ", message=" + message + "]";
	}
	
	
}
