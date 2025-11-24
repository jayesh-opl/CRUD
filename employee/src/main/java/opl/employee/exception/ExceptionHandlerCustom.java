package opl.employee.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import opl.employee.dto.ResponseContainerDto;
import opl.employee.util.ResponseUtils;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceptionHandlerCustom {

	@ExceptionHandler(value = { BindException.class })
	public ResponseContainerDto<Map<String, String>> handleMethodArgumentNotValidException(BindException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return getResponse(errors, "Mandatory field should not blank", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { RestException.class })
	public ResponseContainerDto<Object> handleRestException(RestException ex) {
		return getResponse(ex.getBody(), ex.getMessage(), ex.getStatus());
	}

	@ExceptionHandler(value = { Exception.class })
	public <T> ResponseContainerDto<T> handleException(Exception ex) {
		return getResponse(null, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { HttpClientErrorException.class })
	public ResponseContainerDto<Map<String, Object>> handleClientAbortException(HttpClientErrorException ex) {
		return getResponse(new JSONObject(ex.getResponseBodyAsString()).toMap(), ex.getMessage(), HttpStatus.valueOf(ex.getStatusCode().value()));
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseContainerDto<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return getResponse(null, Objects.nonNull(ex.getRootCause()) && StringUtils.isNotBlank(ex.getRootCause().getMessage()) ? ex.getRootCause().getMessage() : "Request body missing", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public <T> ResponseContainerDto<T> handleHttpRequestMethodNotSupportedExceptionn() {
		return getResponse(null, "Request not allowed", HttpStatus.METHOD_NOT_ALLOWED);
	}

	private <T> ResponseContainerDto<T> getResponse(T body, String msg, HttpStatus status) {
		return ResponseUtils.generateResponseDto(body, msg, status);
	}

}