import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { ViewChild, Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Student } from '../../models/student';

import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  @ViewChild('loginForm')
  loginForm!: NgForm;

  @Output('modelTitle') modelTitle: EventEmitter<String> = new EventEmitter();

  loginType: string = 'Student';

  formError: string = '';

  studentData: Student = {} as Student;

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login(this.studentData).subscribe({
      next: (data: any) => {
        window.sessionStorage.setItem('token', data.token);
        window.localStorage.setItem('userId', data.user.studentId);
        window.localStorage.setItem('email', data.user.email);
        window.localStorage.setItem('role', data.user.role);
        this.loginForm.reset();
        let email = window.localStorage.getItem("email");
        Swal.fire({
          title: "Login Successful!",
          text: "Welcome, " + email + "!",
          icon: "success"
        });
        if (data.user.role === "ADMIN") {
          this.router.navigate(["/admin"]);
        }
        else if (data.user.role === "STUDENT") {
          this.router.navigate(["/student"]);
        }
      },
      error: (err: any) => {
        Swal.fire({
          icon: "error",
          title: "Login failed!",
          text: err.error.message
        });
        this.loginForm.reset();
      },
    });
  }

}
