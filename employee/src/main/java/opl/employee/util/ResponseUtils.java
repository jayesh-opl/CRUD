package opl.employee.util;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import opl.employee.dto.ResponseContainerDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

	public static <T> ResponseContainerDto<T> generateResponseDto(T object) {
		return new ResponseContainerDto<>(object);
	}

	public static <T> ResponseContainerDto<T> generateResponseDto(T o, String message) {
		return new ResponseContainerDto<T>(o).setSuccessMessage(message);
	}

	public static <T> ResponseContainerDto<T> generateResponseDto(T o, String message, HttpStatus status) {
		return new ResponseContainerDto<T>(o).setErrorMessage(message, status);
	}

}