import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Course } from '../models/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  addCourse(course: any) {
    return this.http.post<Course>(environment.rootUrl + AppConstants.ADD_COURSE_API_URL, course);
  }

  viewAllCourses() {
    return this.http.get<Course[]>(environment.rootUrl + AppConstants.VIEW_ALL_COURSES_API_URL);
  }

  viewCourseById(courseId: any) {
    // This is working with `` only, and not ""
    return this.http.get<Course>(environment.rootUrl + AppConstants.VIEW_COURSE_BY_ID_API_URL + `${courseId}`);
  }

  viewCourseByName(courseName: string) {
    return this.http.get<Course>(environment.rootUrl + AppConstants.VIEW_COURSE_BY_NAME_API_URL + `${courseName}`);
  }

  viewCoursesByCategoryId(categoryId: string) {
    return this.http.get<Course[]>(environment.rootUrl + AppConstants.VIEW_COURSES_BY_CATEGORY_API_URL + `${categoryId}`);
  }

  viewCoursesByNameLike(name: string) {
    return this.http.get<Course[]>(environment.rootUrl + AppConstants.VIEW_COURSES_BY_NAME_LIKE_API_URL + `${name}`);
  }

  updateCourse(course: any) {
    return this.http.put<Course>(environment.rootUrl + AppConstants.UPDATE_COURSE_API_URL, course);
  }

  deleteCourse(courseId: string) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_COURSE_API_URL + `${courseId}`, { responseType: "text" as "json" });
  }

}
