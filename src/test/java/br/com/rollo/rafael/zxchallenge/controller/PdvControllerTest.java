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
	
	private PdvDto validPdv = PdvDtoBuilder.buildFromValidJson();
	private PdvDto invalidPdv = PdvDtoBuilder.buildFromInvalidJson();

	@Test
	public void shouldReturnCreatedStatusOnCreatePdv() throws Exception {
		
		ResponseEntity<PdvDto> response = createPdv(validPdv, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void shouldReturnBadRequestOnCreatePdv() throws Exception {

		ResponseEntity<ValidationErrorDto> response = createPdv(invalidPdv, ValidationErrorDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		ValidationErrorDto errors = response.getBody();
		assertThat(errors.getNumberOfErrors()).isEqualTo(3);
	}
	
	@Test
	public void shouldReturnFoundPdvOnGetById() throws Exception {
		
		createPdv(validPdv, PdvDto.class);
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/1"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		PdvDto pdv = response.getBody();
		assertThat(pdv.getId()).isEqualTo(1);
		assertThat(pdv).isEqualTo(validPdv);
		
	}

	@Test
	public void shouldReturnPdvNotFoundOnGetById() throws Exception {
		
		createPdv(validPdv, PdvDto.class);
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/25"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	private <T> ResponseEntity<T> createPdv(PdvDto pdv, Class<T> expectedResponseType) throws Exception {

		RequestEntity<PdvDto> request = RequestEntity
				.post(new URI("/api/pdv"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(pdv);

		return this.testRestTemplate.exchange(request, expectedResponseType);
	}
}
