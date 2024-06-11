import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Enrollment } from '../models/enrollment';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  constructor(private http: HttpClient) { }

  addEnrollment(enrollment: Enrollment) {
    return this.http.post<Enrollment>(environment.rootUrl + AppConstants.ADD_ENROLLMENT_API_URL, enrollment);
  }

  updateEnrollment(enrollment: Enrollment) {
    return this.http.put<Enrollment>(environment.rootUrl + AppConstants.UPDATE_ENROLLMENT_API_URL, enrollment);
  }

  completionUpdate(enrollment: Enrollment) {
    return this.http.put<Enrollment>(environment.rootUrl + AppConstants.COMPLETION_UPDATE_API_URL, enrollment);
  }

  viewAllEnrollments() {
    return this.http.get<Enrollment[]>(environment.rootUrl + AppConstants.VIEW_ALL_ENROLLMENTS_API_URL);
  }

  viewAllEnrollmentsByStudentId(studentId: number) {
    return this.http.get<Enrollment[]>(environment.rootUrl + AppConstants.VIEW_ALL_ENROLLMENTS_BY_STUDENT_ID_API_URL + `${studentId}`);
  }

  viewAllEnrollmentsByCourseId(courseId: string) {
    return this.http.get<Enrollment[]>(environment.rootUrl + AppConstants.VIEW_ALL_ENROLLMENTS_BY_COURSE_ID_API_URL + `${courseId}`);
  }

  viewEnrollmentById(enrollmentId: number) {
    return this.http.get<Enrollment>(environment.rootUrl + AppConstants.VIEW_ENROLLMENT_BY_ID_API_URL + `${enrollmentId}`);
  }

  deleteEnrollment(enrollmentId: number) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_ENROLLMENT_API_URL + `${enrollmentId}`, { responseType: "text" as "json" });
  }
}
