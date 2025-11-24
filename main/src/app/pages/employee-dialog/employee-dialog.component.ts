import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MaterialModule } from 'src/app/material.module';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employee-dialog',
  templateUrl: './employee-dialog.component.html',
  styleUrls: ['./employee-dialog.component.scss'],
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatDialogModule,
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MaterialModule
  ]
})
export class EmployeeDialogComponent implements OnInit {
  employeeForm: FormGroup;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EmployeeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private employeeService: EmployeeService,
  ) { }

  ngOnInit(): void {
    this.isEditMode = !!this.data?.employee;

    this.employeeForm = this.fb.group({
      id: [this.data?.employee?.id || null],
      firstName: [
        this.data?.employee?.firstName || '',
        [
          Validators.required,
          Validators.pattern('^[A-Za-z]+$'),
          Validators.maxLength(50)
        ]
      ],
      lastName: [
        this.data?.employee?.lastName || '',
        [
          Validators.required,
          Validators.pattern('^[A-Za-z]+$'),
          Validators.maxLength(50)
        ]
      ],
      emailAddress: [
        this.data?.employee?.emailAddress || '',
        [
          Validators.required,
          Validators.email,
          Validators.maxLength(100)
        ]
      ],
      mobileNumber: [
        this.data?.employee?.mobileNumber || '',
        [
          Validators.required,
          Validators.pattern('^[1-9]\\d{6,14}$'),
          Validators.maxLength(15)
        ]
      ],
      department: [
        this.data?.employee?.department || '',
        [Validators.maxLength(100)]
      ],
      designation: [
        this.data?.employee?.designation || '',
        [Validators.maxLength(100)]
      ]
    });

  }

  onSubmit() {
    if (this.employeeForm.valid) {
      Object.keys(this.employeeForm.controls).forEach(field => {
        const control = this.employeeForm.get(field);
        if (control?.hasError('backend')) {
          control.setErrors(null);
        }
      });
      this.employeeService.addUpdate(this.employeeForm.value).subscribe((res) => {
        if (res.isError && res.body) {
          for (const field in res.body) {
            if (this.employeeForm.controls[field]) {
              this.employeeForm.controls[field].setErrors({ backend: res.body[field] });
            }
          }
        } else {
          this.dialogRef.close();
        }
      });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }
}