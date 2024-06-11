import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Student } from '../../../models/student';
import { StudentService } from '../../../services/student.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-students-page',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './students-page.component.html',
  styleUrl: './students-page.component.css'
})
export class StudentsPageComponent {
  students: Student[] = [];
  status: boolean = true;
  constructor(private studentService: StudentService, private router: Router) { }

  ngOnInit() {
    this.viewAllStudents();
  }

  viewAllStudents() {
    this.studentService.viewAllStudents().subscribe({
      next: (data: Student[]) => {
        this.students = data;
        this.students.shift();
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

  deleteStudent(studentId: any) {
    Swal.fire({
      title: "Are you sure you want to delete this student?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.studentService.deleteStudent(studentId).subscribe({
          next: (data: string) => {
            Swal.fire({
              title: "Deleted!",
              text: data,
              icon: "success"
            });
            this.viewAllStudents();
            this.router.navigate(["/students-page"]);
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
    });

  }
}
