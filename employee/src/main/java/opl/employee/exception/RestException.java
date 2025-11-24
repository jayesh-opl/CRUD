package opl.employee.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final String message;
	private final Object body;

	public RestException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		this.body = null;
	}

	public RestException(HttpStatus status, String message, Object o) {
		this.status = status;
		this.message = message;
		this.body = o;
	}

}