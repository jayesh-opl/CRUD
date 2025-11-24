package opl.employee.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseContainerDto<T> {

	private String message;
	private Integer status;
	private Boolean isError;
	private T body;

	public ResponseContainerDto(T object) {
		this.message = "Success";
		this.isError = Boolean.FALSE;
		this.status = HttpStatus.OK.value();
		this.body = object;
	}

	public ResponseContainerDto<T> setSuccessMessage(String message) {
		this.message = message;
		this.isError = Boolean.FALSE;
		this.status = HttpStatus.OK.value();
		return this;
	}

	public ResponseContainerDto<T> setErrorMessage(String message, HttpStatus status) {
		this.message = message;
		this.isError = Boolean.TRUE;
		this.status = status.value();
		return this;
	}

}