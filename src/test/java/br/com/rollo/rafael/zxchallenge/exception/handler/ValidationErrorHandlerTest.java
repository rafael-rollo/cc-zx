package br.com.rollo.rafael.zxchallenge.exception.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.rollo.rafael.zxchallenge.validation.dto.FieldErrorDto;
import br.com.rollo.rafael.zxchallenge.validation.dto.ValidationErrorDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ValidationErrorHandlerTest {
	
	private static final String DEFAULT_MESSAGE = "Default message";
	private static final String TRADING_NAME_MESSAGE = "A trading name personal error message";
	private static final String OWNER_NAME_MESSAGE = "An owner name personal error message";
	private static final String DOCUMENT_MESSAGE = "A document personal error message";
	private static final String ADDRESS_CODE = "An address most expressive code";

	@MockBean
	private MessageSource messageSource;
	
	@MockBean
	private BindingResult bindingResult;
	
	@MockBean
	private MethodArgumentNotValidException exception;
	
	@Before
	public void setup() {
		FieldError tradingNameError = new FieldError("pdvDto", "tradingName", DEFAULT_MESSAGE);
		FieldError ownerNameError = new FieldError("pdvDto", "ownerName", DEFAULT_MESSAGE);
		FieldError documentError = new FieldError("pdvDto", "document", DEFAULT_MESSAGE);
		FieldError addressError = new FieldError("pdvDto", "address", null, true, 
				new String[] {"address.error.code"}, null, DEFAULT_MESSAGE);
		
		List<FieldError> fieldErrors = Arrays.asList(tradingNameError, ownerNameError, documentError, addressError);

		given(this.bindingResult.getFieldErrors()).willReturn(fieldErrors);
		
		Locale locale = LocaleContextHolder.getLocale();
		given(this.messageSource.getMessage(tradingNameError, locale)).willReturn(TRADING_NAME_MESSAGE);
		given(this.messageSource.getMessage(ownerNameError, locale)).willReturn(OWNER_NAME_MESSAGE);
		given(this.messageSource.getMessage(documentError, locale)).willReturn(DOCUMENT_MESSAGE);
		given(this.messageSource.getMessage(addressError, locale)).willReturn(ADDRESS_CODE);
		
		given(this.exception.getBindingResult()).willReturn(bindingResult);
	}
	
	@Test
	public void shouldProcessValidationErrors() {
	
		ValidationErrorHandler handler = new ValidationErrorHandler(this.messageSource);
		ValidationErrorDto validationErrorDto = handler.processValidationError(exception);
		
		FieldErrorDto tradingNameErrorDto = new FieldErrorDto("tradingName", TRADING_NAME_MESSAGE);
		FieldErrorDto ownerNameErrorDto = new FieldErrorDto("ownerName", OWNER_NAME_MESSAGE);
		FieldErrorDto documentErrorDto = new FieldErrorDto("document", DOCUMENT_MESSAGE);
		FieldErrorDto addressErrorDto = new FieldErrorDto("address", ADDRESS_CODE);
		
		assertThat(validationErrorDto.getNumberOfErrors()).isEqualTo(4);
		assertThat(validationErrorDto.getFieldErrors())
				.containsExactly(tradingNameErrorDto, ownerNameErrorDto, documentErrorDto, addressErrorDto);
	}
}
