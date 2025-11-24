package opl.employee.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class EmployeeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(updatable = false)
	private UUID id;

	private String firstName;

	private String lastName;

	private String emailAddress;

	private String mobileNumber;

	private String department;

	private String designation;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@LastModifiedBy
	private String updatedBy;

}