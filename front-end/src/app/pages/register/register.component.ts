import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, RouterLinkActive],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  firstName = new FormControl("", [
    Validators.required,
    Validators.minLength(3),
    Validators.pattern("[A-Za-z ]+"),
  ]);

  lastName = new FormControl("", [
    Validators.required,
    Validators.pattern("[A-Za-z ]*")
  ]);

  email = new FormControl("", [
    Validators.required,
    Validators.email,
    Validators.pattern("[A-Za-z0-9.]+@[A-Za-z]{3,15}.[A-Za-z]{2,3}")
  ]);

  mobile = new FormControl("", [
    Validators.required,
    Validators.pattern("[0-9+-]{10,15}"),
  ]);

  password = new FormControl("", [
    Validators.required,
    Validators.pattern("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
  ]);

  confirmPassword = new FormControl("", [
    Validators.required,
    Validators.pattern("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
  ]);

  registerForm = new FormGroup({
    firstName: this.firstName,
    lastName: this.lastName,
    email: this.email,
    mobile: this.mobile,
    password: this.password,
    confirmPassword: this.confirmPassword
  });

  errors: string[] = []
  constructor(private authService: AuthService, private router: Router) { }

  check: boolean = false;

  register() {
    this.check = this.registerForm.value.password !== this.registerForm.value.confirmPassword;
    if (this.check === false) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (data: any) => {
          window.sessionStorage.setItem('token', data.token);
          window.localStorage.setItem('userId', data.user.studentId);
          window.localStorage.setItem('email', data.user.email);
          window.localStorage.setItem('role', data.user.role);
          this.registerForm.reset();
          let userEmail = window.localStorage.getItem("email"); 
          Swal.fire({
            title: "Registration Successful!",
            text: "Welcome, " + userEmail + "!",
            icon: "success"
          });
          this.router.navigate(["/student"]);
        },
        error: (err: any) => {
          this.errors = err.error.message;
          Swal.fire({
            icon: "error",
            title: "Something went wrong..!",
            text: err.error.message
          });
        },
      });
    }

  }

}
