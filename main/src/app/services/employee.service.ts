import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../pages/employee/employee';
import { ResponseContainer } from '../pages/response-container';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrl = 'http://localhost:8080/employee';

  constructor(
    private http: HttpClient
  ) { }

  getEmployees(search: string, page: number, size: number, sortField: string, sortDir: string): Observable<ResponseContainer<any>> {
    const params: any = {
      page,
      size,
      sortDir,
    };

    if (sortField) params.sortField = sortField;
    if (search) params.search = search;
    return this.http.get<ResponseContainer<any>>(this.apiUrl, { params });
  }

  addUpdate(employee: Employee): Observable<ResponseContainer<any>> {
    return this.http.post<ResponseContainer<any>>(this.apiUrl, employee);
  }

  delete(id: string): Observable<ResponseContainer<any>> {
    return this.http.delete<ResponseContainer<any>>(`${this.apiUrl}/${id}`)
  }

}