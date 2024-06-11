import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Student } from '../models/student';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  register(registerForm: any) {
    return this.http.post<{ token: string, student: Student }>(environment.rootUrl + AppConstants.REGISTER_API_URL, registerForm,
      {
        withCredentials: true,
      });
  }

  login(student: Student) {
    return this.http.post<{ token: string, student: Student }>(
      'http://localhost:8081/learnvilla/api/login',
      student,
      {
        withCredentials: true,
      }
    );
  }

}

