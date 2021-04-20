package br.com.stoom.desafio.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.stoom.desafio.modelo.dto.ErrorDTO;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDTO> hndle(MethodArgumentNotValidException excpetion) {
		List<ErrorDTO> dto = new ArrayList<ErrorDTO>();
		
		List<FieldError> fieldErrors = excpetion.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e,  LocaleContextHolder.getLocale());
			ErrorDTO erro = new ErrorDTO(e.getField(), mensagem);
			
			dto.add(erro);
		});
		
		return dto;
	}
	
}
