import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { EmployeeService } from 'src/app/services/employee.service';
import { Employee } from './employee';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MaterialModule } from 'src/app/material.module';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeDialogComponent } from '../employee-dialog/employee-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-employee',
  imports: [MatTableModule,
    CommonModule,
    MatCardModule,
    MaterialModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.scss'
})
export class EmployeeComponent implements OnInit {

  displayedColumns: string[] = ['#', 'name', 'mobileNumber', 'emailAddress', 'action'];
  employees: Employee[] = [];
  totalItems = 0;
  pageSize = 10;
  currentPage = 0;
  sortField: string = '';
  sortDirection: string = '';
  searchValue: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private employeeService: EmployeeService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.loadEmployees();
  }

  loadEmployees() {
    this.employeeService
      .getEmployees(this.searchValue, this.currentPage, this.pageSize, this.sortField, this.sortDirection)
      .subscribe(res => {
        this.employees = res.body.content;
        this.totalItems = res.body.totalElements;
      });
  }

  onPageChange(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.loadEmployees();
  }

  onSortChange(sort: Sort) {
    this.sortField = sort.active === 'name' ? 'firstName' : sort.active;
    this.sortDirection = sort.direction;
    this.loadEmployees();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.searchValue = filterValue.trim().toLowerCase();
    this.currentPage = 0;
    this.loadEmployees();
  }

  getFullName(emp: Employee): string {
    return `${emp.firstName} ${emp.lastName}`;
  }

  openDialog(employee: Employee | null) {
    const dialogRef = this.dialog.open(EmployeeDialogComponent, {
      width: '500px',
      data: { employee }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadEmployees();
    });
  }

  deleteEmployee(employee: Employee) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: { message: `Are you sure you want to delete ${employee.firstName} ${employee.lastName}?` }
    });

    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.employeeService.delete(employee.id).subscribe(() => {
          this.loadEmployees();
        });
      }
    });
  }

}