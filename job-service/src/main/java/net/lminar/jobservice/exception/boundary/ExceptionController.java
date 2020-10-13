package net.lminar.jobservice.exception.boundary;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception controller.
 *
 * @author Lukas Minar
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

	/**
	 * Returns 403 FORBIDDEN - entity not found.
	 *
	 * @param e exception
	 * @return response with 403 status code
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity handleException(EntityNotFoundException e) {
		log.error("Handle exception.", e);
		return new ResponseEntity(FORBIDDEN);
	}

	/**
	 * Returns 400 BAD_REQUEST - data not valid.
	 *
	 * @param e exception
	 * @return response with 400 status code
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getAllErrors()
			.forEach((error) ->
				errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
