package br.com.rollo.rafael.zxchallenge.exception.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.rollo.rafael.zxchallenge.validation.dto.ValidationErrorDto;

@ControllerAdvice
public class ValidationErrorHandler {

	private MessageSource messageSource;
	
    @Autowired
    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorDto processValidationError(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
 
        ValidationErrorDto validationErrorDto = processFieldErrors(fieldErrors);
        return validationErrorDto;
    }
 
    private ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDto validationErrors = new ValidationErrorDto();
 
        for (FieldError fieldError: fieldErrors) {
            String errorMessage = getMessageFor(fieldError);
            validationErrors.addFieldError(fieldError.getField(), errorMessage);
        }
 
        return validationErrors;
    }
 
    /**
     * Fallback message is the most expressive error code
     * @param fieldError
     * @return
     */
    private String getMessageFor(FieldError fieldError) {
        String foundMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    	
        if (fieldError.getDefaultMessage().equals(foundMessage))
            foundMessage = fieldError.getCodes()[0];
            
        return foundMessage;
    }
}

