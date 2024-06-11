import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { Enrollment } from '../../../models/enrollment';
import { CourseService } from '../../../services/course.service';
import { EnrollmentService } from '../../../services/enrollment.service';
import { StudentNavComponent } from '../student-nav/student-nav.component';

@Component({
  selector: 'app-my-enrollments',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, StudentNavComponent, DatePipe],
  templateUrl: './my-enrollments.component.html',
  styleUrl: './my-enrollments.component.css'
})
export class MyEnrollmentsComponent {
  enrollments: Enrollment[] = [];
  errors: string[] = [];
  courseId: any;
  course: Course = {} as Course;
  courses: Course[] = [];
  constructor(private enrollmentService: EnrollmentService, private courseService: CourseService, private router: Router) { }

  ngOnInit() {
    this.viewEnrollmentsByStudentId(Number(window.localStorage.getItem("userId")));
  }

  viewEnrollmentsByStudentId(studentId: number) {
    this.enrollmentService.viewAllEnrollmentsByStudentId(studentId).subscribe({
      next: (data: any) => {
        this.enrollments = data;
        for (let i = 0; i < this.enrollments.length; i++) {
          this.courseId = this.enrollments[i].courseId;
          this.getCourseById(this.courseId).subscribe((data: any) => {
            this.course = data;
            this.course.enrolledDate = this.enrollments[i].enrolledDate;
            this.courses.push(this.course);
          });
        }
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message
        });
        this.router.navigate(["/student"]);
      }
    });
  }

  getCourseById(courseId: any) {
    return this.courseService.viewCourseById(this.courseId);
  }
  
}
