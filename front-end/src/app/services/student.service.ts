import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Student } from '../models/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  constructor(private http: HttpClient) { }

  viewAllStudents() {
    return this.http.get<Student[]>(environment.rootUrl + AppConstants.VIEW_ALL_STUDENTS_API_URL);
  }

  viewStudentById(studentId: number) {
    return this.http.get<Student>(environment.rootUrl + AppConstants.VIEW_STUDENT_BY_ID_API_URL + `${studentId}`);
  }

  updateStudent(student: Student) {
    return this.http.put<Student>(environment.rootUrl + AppConstants.UPDATE_STUDENT_API_URL, student);
  }

  deleteStudent(studentId: number) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_STUDENT_API_URL + `${studentId}`, { responseType: "text" as "json" });
  }
}

// {
//   headers: new HttpHeaders({
//     Authorization: "Bearer " + window.sessionStorage.getItem("token")
//   },
