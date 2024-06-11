import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { CourseService } from '../../../services/course.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-courses-page',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './courses-page.component.html',
  styleUrl: './courses-page.component.css'
})
export class CoursesPageComponent {
  courses: Course[] = [];
  constructor(private courseService: CourseService, private router :Router) { }

  ngOnInit() {
    this.viewAllCourses();
  }

  viewAllCourses() {
    this.courseService.viewAllCourses().subscribe({
      next: (data: Course[]) => {
        console.log(data);
        this.courses = data;
      },
      error: (err) => {
        alert(err.error.message);
        console.log("Errors", err);
      }
    });
  }

  deleteCourse(courseId : string) {
    Swal.fire({
      title: "Are you sure you want to delete this course?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.courseService.deleteCourse(courseId).subscribe({
          next : (data:string) => {
            Swal.fire({
              title: "Deleted!",
              text: data,
              icon: "success"
            });
            this.viewAllCourses();
          },
          error : (err) => {
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
