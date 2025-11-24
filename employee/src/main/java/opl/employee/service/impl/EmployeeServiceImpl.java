package opl.employee.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import opl.employee.dto.EmployeeDto;
import opl.employee.exception.ErrorGenerator;
import opl.employee.exception.ExceptionService;
import opl.employee.model.EmployeeModel;
import opl.employee.repository.EmployeeRepository;
import opl.employee.service.EmployeeService;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeServiceImpl implements EmployeeService {

	EmployeeRepository employeeRepository;

	ModelMapper modelMapper;

	ExceptionService exceptionService;

	@Override
	public Page<EmployeeDto> getAll(String keyword, int page, int size, String sortField, String sortDir) {
		Pageable pageable;
		if (sortField == null || sortField.trim().isEmpty()) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
			pageable = PageRequest.of(page, size, sort);
		}
		Specification<EmployeeModel> spec = contains(keyword);
		Page<EmployeeModel> employeeModelPage = employeeRepository.findAll(spec, pageable);
		return employeeModelPage.map(emp -> modelMapper.map(emp, EmployeeDto.class));
	}

	@Override
	public EmployeeDto saveUpdate(EmployeeDto employeeDto) {
		ErrorGenerator errors = ErrorGenerator.builder();
		UUID id = Objects.nonNull(employeeDto.getId()) ? UUID.fromString(employeeDto.getId()) : null;
		boolean isMobileExist = Objects.nonNull(id)
				? employeeRepository.existsByMobileNumberAndIdNot(employeeDto.getMobileNumber(), id)
				: employeeRepository.existsByMobileNumber(employeeDto.getMobileNumber());
		if (isMobileExist) {
			errors.putError("mobileNumber", "Mobile number already exist");
		}
		boolean isEmailAddressExist = Objects.nonNull(id)
				? employeeRepository.existsByEmailAddressAndIdNot(employeeDto.getEmailAddress(), id)
				: employeeRepository.existsByEmailAddress(employeeDto.getEmailAddress());
		if (isEmailAddressExist) {
			errors.putError("emailAddress", "Email address already exist");
		}
		if (Objects.nonNull(id) && !employeeRepository.existsById(id)) {
			errors.putError("id", "record doesn't exist by given id");
		}
		if (errors.hasError()) {
			exceptionService.throwBadRequestException("Invalid Input", errors.getErrors());
		}
		EmployeeModel employeeModel = modelMapper.map(employeeDto, EmployeeModel.class);
		employeeRepository.save(employeeModel);
		return modelMapper.map(employeeModel, EmployeeDto.class);
	}

	@Override
	public void delete(UUID uuid) {
		employeeRepository.deleteById(uuid);
	}

	private Specification<EmployeeModel> contains(String keyword) {
		return (root, query, cb) -> {
			if (keyword == null || keyword.trim().isEmpty()) {
				return cb.conjunction();
			}

			String likePattern = "%" + keyword.toLowerCase() + "%";

			return cb.or(cb.like(cb.lower(root.get("firstName")), likePattern),
					cb.like(cb.lower(root.get("lastName")), likePattern),
					cb.like(cb.lower(root.get("emailAddress")), likePattern),
					cb.like(cb.lower(root.get("mobileNumber")), likePattern));
		};
	}

}