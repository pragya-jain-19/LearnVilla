import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Student } from '../../../models/student';
import { StudentService } from '../../../services/student.service';

@Component({
  selector: 'app-admin-profile',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent {
  // userId: any;
  student: Student = {} as Student;
  change: boolean = false;
  stuId: any;
  fname: any;
  lname: any;
  uname: any;
  mobile: any;
  password: any;
  confirmPassword: any;

  constructor(private studentService: StudentService) { }

  ngOnInit() {
    this.viewStudentById(1);
  }

  viewStudentById(studentId: number) {
    this.studentService.viewStudentById(studentId).subscribe({
      next: (data: Student) => {
        this.student = data;
        this.stuId = this.student.studentId;
        this.fname = this.student.firstName;
        this.lname = this.student.lastName;
        this.uname = this.student.email;
        this.mobile = this.student.mobile;
        this.password = this.student.password;
        this.confirmPassword = this.student.confirmPassword;
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
      }
    });
  }

  changePassword() {
    this.change = true;
  }

  updateProfile(updateProfileForm: NgForm) {
    this.studentService.updateStudent({
      studentId: this.student.studentId,
      firstName: updateProfileForm.value.fname,
      lastName: updateProfileForm.value.lname,
      mobile : updateProfileForm.value.mobile,
      email: updateProfileForm.value.uname,
      password: updateProfileForm.value.password ? updateProfileForm.value.password : this.student.password,
      confirmPassword: updateProfileForm.value.confirmPassword ? updateProfileForm.value.confirmPassword : this.student.confirmPassword,
      isActive: this.student.isActive
    }).subscribe({
      next: (data: any) => {
        this.student = data;
        this.change = false;
        Swal.fire({
          icon: "success",
          title: "Updated!",
          text: "Profile updated successfully..!",
        });
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
      }
    });
  }
}
