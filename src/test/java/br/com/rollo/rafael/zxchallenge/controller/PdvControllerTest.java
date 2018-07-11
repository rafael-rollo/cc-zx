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
import br.com.rollo.rafael.zxchallenge.repository.PdvRepository;
import br.com.rollo.rafael.zxchallenge.validation.dto.ValidationErrorDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PdvControllerTest {
	
	private static final String WKT_OF_COVERAGE_AREA = "MULTIPOLYGON ((("
			+ "10 20, 30 0, 70 10, 100 10, 110 30, 100 50, "
			+ "110 70, 60 80, 30 90, 10 60, 30 40, 10 20)))";

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PdvRepository pdvRepository;
	
	private PdvDto validPdv = PdvDtoBuilder.buildFromValidJson();
	private PdvDto invalidPdv = PdvDtoBuilder.buildFromInvalidJson();
	
	private PdvDto deliveryDoZe = PdvDtoBuilder.newPdv()
			.withTradingName("Delivery do Zé")
			.withCoverageArea(WKT_OF_COVERAGE_AREA)
			.withAddress("POINT (70 40)")
			.build();
		
	private PdvDto barDoManoel = PdvDtoBuilder.newPdv()
			.withTradingName("Bar do Manoel")
			.withCoverageArea(WKT_OF_COVERAGE_AREA)
			.withAddress("POINT (50 50)")
			.build();

	@Test
	public void shouldReturnCreatedStatusOnCreatePdv() throws Exception {
		
		ResponseEntity<PdvDto> response = createPdv(this.validPdv, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void shouldReturnBadRequestOnCreatePdv() throws Exception {

		ResponseEntity<ValidationErrorDto> response = createPdv(this.invalidPdv, ValidationErrorDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		ValidationErrorDto errors = response.getBody();
		assertThat(errors.getNumberOfErrors()).isEqualTo(3);
	}
	
	@Test
	public void shouldReturnFoundPdvOnGetById() throws Exception {
		
		createPdv(this.validPdv, PdvDto.class);
		
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

		createPdv(this.validPdv, PdvDto.class);
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/529"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}
	
	@Test
	public void shouldReturnBarDoManoelAsPdvOnSearch() throws Exception {
		
		pdvRepository.save(this.deliveryDoZe.toPdv());
		pdvRepository.save(this.barDoManoel.toPdv());
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/search?longitude=30&latitude=20"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTradingName()).isEqualTo(barDoManoel.getTradingName());
	}
	
	@Test
	public void shouldReturnDeliveryDoZeAsPdvOnSearch() throws Exception {
		
		pdvRepository.save(this.deliveryDoZe.toPdv());
		pdvRepository.save(this.barDoManoel.toPdv());
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/search?longitude=70&latitude=20"))
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<PdvDto> response = this.testRestTemplate.exchange(request, PdvDto.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTradingName()).isEqualTo(deliveryDoZe.getTradingName());
	}

	@Test
	public void shouldReturnPdvNotFoundOnSearch() throws Exception {
		
		pdvRepository.save(this.deliveryDoZe.toPdv());
		pdvRepository.save(this.barDoManoel.toPdv());
		
		RequestEntity<Void> request = RequestEntity
				.get(new URI("/api/pdv/search?longitude=10&latitude=80"))
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
