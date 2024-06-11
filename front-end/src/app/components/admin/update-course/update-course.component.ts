import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { CourseService } from '../../../services/course.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-update-course',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent],
  templateUrl: './update-course.component.html',
  styleUrl: './update-course.component.css'
})
export class UpdateCourseComponent {
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

  updateCourseForm = new FormGroup({
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

  id: any;
  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get("courseId");
    this.viewCourseById(this.id);
  }

  course: Course = {} as Course;
  viewCourseById(courseId: string) {
    this.courseService.viewCourseById(courseId).subscribe({
      next: (data: any) => {
        this.course = data;
        this.updateFormInStarting(this.course, data.category.categoryId);
      }
    });
  }

  updateFormInStarting(course: Course, categoryId: string) {
    this.updateCourseForm.patchValue({
      courseId: course.courseId,
      courseName: course.courseName,
      description: course.description,
      categoryId: categoryId,
      duration: course.duration?.toString(),
      price: course.price?.toString(),
      studentsEnrolled: course.studentsEnrolled?.toString(),
      rating: course.rating?.toString(),
      courseImage: course.courseImage,
      language: course.language
    });
  }

  constructor(private courseService: CourseService, private router: Router, private route: ActivatedRoute) { }

  updateCourse() {
    this.updateCourseForm.value.courseId = this.id;
    this.courseService.updateCourse(this.updateCourseForm.value).subscribe({
      next: (data: any) => {
        Swal.fire({
          title: "Updated!",
          text: "Course Updated successfully..!",
          icon: "success"
        });
        this.router.navigate(["/courses-page"]);
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
