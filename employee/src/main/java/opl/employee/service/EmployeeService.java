package opl.employee.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import opl.employee.dto.EmployeeDto;

public interface EmployeeService {

	Page<EmployeeDto> getAll(String keyword, int page, int size, String sortField, String sortDir);

	EmployeeDto saveUpdate(EmployeeDto employeeDto);

	void delete(UUID uuid);

}