package opl.employee.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import opl.employee.dto.EmployeeDto;
import opl.employee.dto.ResponseContainerDto;
import opl.employee.exception.ExceptionService;
import opl.employee.service.EmployeeService;
import opl.employee.util.ResponseUtils;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeController {

	EmployeeService employeeService;

	ExceptionService exceptionService;

	@GetMapping
	public ResponseContainerDto<Page<EmployeeDto>> getAll(
			@RequestParam(name="search", required = false) String search,
	        @RequestParam(name="page", defaultValue = "0") int page,
	        @RequestParam(name="size", defaultValue = "10") int size,
	        @RequestParam(name="sortField", required = false) String sortField,
	        @RequestParam(name="sortDir", defaultValue = "asc") String sortDir) {
		return ResponseUtils.generateResponseDto(employeeService.getAll(search, page, size, sortField, sortDir));
	}

	@PostMapping
	public ResponseContainerDto<EmployeeDto> saveUpdate(@RequestBody @Valid EmployeeDto employeeDto) {
		return ResponseUtils.generateResponseDto(employeeService.saveUpdate(employeeDto));
	}

	@DeleteMapping("/{id}")
	public ResponseContainerDto<Object> delete(@PathVariable("id") String id) {
		try {
			UUID uuid = UUID.fromString(id);
			employeeService.delete(uuid);
			return ResponseUtils.generateResponseDto(null, "Deleted.");
		} catch (IllegalArgumentException e) {
			exceptionService.throwBadRequestException(e.getMessage());
		}
		return null;
	}

}