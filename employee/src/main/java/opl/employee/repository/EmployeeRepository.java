package opl.employee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import opl.employee.model.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, UUID>, JpaSpecificationExecutor<EmployeeModel> {

	boolean existsByMobileNumberAndIdNot(String mobileNumber, UUID id);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmailAddressAndIdNot(String emailAddress, UUID id);

	boolean existsByEmailAddress(String emailAddress);

}