package opl.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

	@org.hibernate.validator.constraints.UUID
	private String id;

	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters (A-Z, a-z)")
	@Size(max = 50, message = "First name must be at most 50 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters (A-Z, a-z)")
	@Size(max = 50, message = "Last name must be at most 50 characters")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	@Size(max = 100, message = "Email address must be at most 100 characters")
	private String emailAddress;

	@NotBlank(message = "Mobile number is required")
	@Size(max = 15, message = "Mobile number must be at most 15 characters")
	@Pattern(regexp = "^[1-9]\\d{6,14}$", message = "Phone number must be 7 to 15 digits long")
	private String mobileNumber;

	@Size(max = 100, message = "Department must be at most 100 characters")
	private String department;

	@Size(max = 100, message = "Designation must be at most 100 characters")
	private String designation;

}