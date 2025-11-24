package opl.employee.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorGenerator {

	private Map<String, Object> errors;

	public static ErrorGenerator builder() {
		return new ErrorGenerator(new HashMap<>());
	}

	public ErrorGenerator putError(String key, Object value) {
		if (Objects.nonNull(errors)) {
			errors.put(key, value);
		}
		return this;
	}

	public boolean hasError() {
		return Objects.nonNull(errors) && !errors.isEmpty();
	}

}