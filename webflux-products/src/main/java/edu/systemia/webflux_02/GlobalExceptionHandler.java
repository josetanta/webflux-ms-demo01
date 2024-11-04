package edu.systemia.webflux_02;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ WebExchangeBindException.class })
	public Mono<Map<String, String>> handleValidationExceptions(
		WebExchangeBindException ex, ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);

		Map<String, String> errors = ex.getFieldErrors().stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				FieldError::getDefaultMessage,
				(message1, message2) -> message1 + "; " + message2
			));

		return Mono.just(errors);
	}
}
