package br.com.rollo.rafael.zxchallenge.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rollo.rafael.zxchallenge.builder.PdvDtoBuilder;
import br.com.rollo.rafael.zxchallenge.controller.dto.PdvDto;
import br.com.rollo.rafael.zxchallenge.validation.dto.ValidationErrorDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PdvControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldReturnCreatedStatusOnCreatePdv() throws Exception {

		PdvDto pdvDto = PdvDtoBuilder.buildFromValidJson();

		RequestEntity<PdvDto> request = RequestEntity
				.post(new URI("/api/pdv"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(pdvDto);

		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}

	@Test
	public void shouldReturnBadRequestOnCreatePdv() throws Exception {

		PdvDto pdvDto = PdvDtoBuilder.buildFromInvalidJson();

		RequestEntity<PdvDto> request = RequestEntity
				.post(new URI("/api/pdv"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(pdvDto);

		ResponseEntity<ValidationErrorDto> response = this.testRestTemplate.exchange(request, ValidationErrorDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		ValidationErrorDto errors = response.getBody();
		assertThat(errors.getNumberOfErrors()).isEqualTo(3);
	}

	
}
