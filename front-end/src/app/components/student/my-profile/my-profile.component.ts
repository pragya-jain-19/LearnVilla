import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Student } from '../../../models/student';
import { StudentService } from '../../../services/student.service';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, FormsModule],
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.css'
})
export class MyProfileComponent {
  userId: any;
  student: Student = {} as Student;
  change: boolean = false;
  isActive: boolean = false;
  stuId: any;
  fname: any;
  lname: any;
  uname: any;
  mobile: any;
  password: any;
  confirmPassword: any;
  isUserActive: any;

  constructor(private route: ActivatedRoute, private studentService: StudentService) { }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get("userId");
    this.viewStudentById(this.userId);
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
        this.isUserActive = !this.student.isActive;
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

  changeStatus() {
    this.isActive = true;
  }

  updateProfile(updateProfileForm: NgForm) {
    this.studentService.updateStudent({
      studentId: this.student.studentId,
      firstName: updateProfileForm.value.fname,
      lastName: updateProfileForm.value.lname,
      email: updateProfileForm.value.uname,
      mobile : updateProfileForm.value.mobile,
      password: updateProfileForm.value.password ? updateProfileForm.value.password : this.student.password,
      confirmPassword: updateProfileForm.value.confirmPassword ? updateProfileForm.value.confirmPassword : this.student.confirmPassword,
      isActive: this.isActive ? updateProfileForm.value.isUserActive : updateProfileForm.value.isUserActive
    }).subscribe({
      next: (data: any) => {
        this.student = data;
        this.change = false;
        this.isActive = false;
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
