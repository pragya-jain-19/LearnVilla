import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CourseService } from '../../../services/course.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-add-course',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent],
  templateUrl: './add-course.component.html',
  styleUrl: './add-course.component.css'
})
export class AddCourseComponent {
  courseId = new FormControl("", [
    Validators.required,
    Validators.pattern("^[A-Z]{2}[0-9]{4}$"),
  ]);

  courseName = new FormControl("", [
    Validators.required,
    Validators.minLength(2)
  ]);

  description = new FormControl("", [
    Validators.required,
    Validators.minLength(2)
  ]);

  categoryId = new FormControl("", [
    Validators.required,
    Validators.pattern("^[A-Z]{2}$"),
  ]);

  duration = new FormControl("");

  price = new FormControl("", [
    Validators.required,
    Validators.min(100)
  ]);

  studentsEnrolled = new FormControl("");

  rating = new FormControl("", [
    Validators.min(0),
    Validators.max(5)
  ]);

  courseImage = new FormControl("");

  language = new FormControl("", [
    Validators.minLength(5)
  ]);

  addCourseForm = new FormGroup({
    courseId: this.courseId,
    courseName: this.courseName,
    description: this.description,
    categoryId: this.categoryId,
    duration: this.duration,
    price: this.price,
    studentsEnrolled: this.studentsEnrolled,
    rating: this.rating,
    courseImage: this.courseImage,
    language: this.language
  });

  errors: string[] = []
  constructor(private courseService: CourseService, private router: Router) { }

  addCourse() {
    this.courseService.addCourse(this.addCourseForm.value).subscribe({
      next: (data: any) => {
        Swal.fire({
          title: "Added!",
          text: "Course Added successfully..!",
          icon: "success"
        });
        this.router.navigate(["/courses-page"]);
      },
      error: (err) => {
        Swal.fire({
          title: "Something went wrong!",
          text: err.error.message,
          icon: "error"
        });
      }
    });
  }
}
