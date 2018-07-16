package br.com.rollo.rafael.zxchallenge.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rollo.rafael.zxchallenge.builder.PdvDtoBuilder;
import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.repository.PdvRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UniqueCnpjValidatorTest {

	@MockBean
	private PdvRepository repository;

	@Test
	public void shouldReportThatCnpjIsValid() {
		String document = "22.222.222/0001-22";

		given(this.repository.findByDocument(document)).willReturn(Optional.empty());
		UniqueCnpjValidator validator = new UniqueCnpjValidator(this.repository);

		assertThat(validator.isValid(document, null)).isTrue();
	}

	@Test
	public void shouldReportThatCnpjIsNotValid() {
		String document = "11.111.111/0001-11";
		Pdv pdv = PdvDtoBuilder.newPdv().withDocument(document).buildAsPdv();

		given(this.repository.findByDocument(document)).willReturn(Optional.of(pdv));
		UniqueCnpjValidator validator = new UniqueCnpjValidator(this.repository);

		assertThat(validator.isValid(document, null)).isFalse();
	}
}
