import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Wishlist } from '../models/wishlist';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  constructor(private http: HttpClient) { }

  insertCourseIntoWishlist(studentId: number, courseId: string) {
    return this.http.post<Wishlist>(environment.rootUrl + AppConstants.INSERT_COURSE_INTO_WISHLIST_API_URL + `${studentId}/${courseId}`, {});
  }

  deleteCourseFromWishlist(studentId: number, courseId: string) {
    return this.http.delete<Wishlist>(environment.rootUrl + AppConstants.DELETE_COURSE_FROM_WISHLIST_API_URL + `${studentId}/${courseId}`);
  }

  viewWishlistByStudentId(studentId: number) {
    return this.http.get<Wishlist>(environment.rootUrl + AppConstants.VIEW_WISHLIST_BY_STUDENT_ID_API_URL + `${studentId}`);
  }

  deleteWishlistByStudentId(studentId: any) {
    return this.http.delete<string>(environment.rootUrl + AppConstants.DELETE_WISHLIST_BY_STUDENT_ID_API_URL + `${studentId}`, { responseType: "text" as "json" });
  }

}
