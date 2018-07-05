package br.com.rollo.rafael.zxchallenge.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.repository.PdvRepository;

@Component
public class UniqueCnpjValidator implements ConstraintValidator<UniqueCNPJ, String> {
	
	private PdvRepository pdvRepository;
	
	@Autowired
	public UniqueCnpjValidator(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}

	@Override
	public boolean isValid(String cnpj, ConstraintValidatorContext context) {

		Optional<Pdv> possiblePdv = pdvRepository.findByDocument(cnpj);
		return ! possiblePdv.isPresent();
	}

}
