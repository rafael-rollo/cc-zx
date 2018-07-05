package br.com.rollo.rafael.zxchallenge.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.br.CNPJ;

@CNPJ
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCnpjValidator.class)
public @interface UniqueCNPJ {

	String message() default
		"{br.com.rollo.rafael.zxchallenge.validation.UniqueCNPJ.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
