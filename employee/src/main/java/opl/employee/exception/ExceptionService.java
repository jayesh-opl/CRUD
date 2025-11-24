package opl.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExceptionService {

	public void throwInternalServerErrorRestException() throws RestException {
		throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", null);
	}

	public void throwRestException(HttpStatus status, String message) throws RestException {
		throw new RestException(status, message);
	}

	public void throwBadRequestException(String message) throws RestException {
		throwRestException(HttpStatus.BAD_REQUEST, message);
	}

	public void throwRestException(HttpStatus status, String message, Object o) throws RestException {
		throw new RestException(status, message, o);
	}

	public void throwBadRequestException(String message, Object o) throws RestException {
		throwRestException(HttpStatus.BAD_REQUEST, message, o);
	}

}