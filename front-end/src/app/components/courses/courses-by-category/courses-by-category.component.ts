import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { CourseService } from '../../../services/course.service';
import { WishlistService } from '../../../services/wishlist.service';

@Component({
  selector: 'app-courses-by-category',
  standalone: true,
  imports: [],
  templateUrl: './courses-by-category.component.html',
  styleUrl: './courses-by-category.component.css'
})
export class CoursesByCategoryComponent {
  courses: Course[] = [];
  categoryId: any;
  errors: string[] = [];
  constructor(private courseService: CourseService, private route: ActivatedRoute, private router: Router, private wishlistService : WishlistService) { }

  ngOnInit() {
    this.categoryId = this.route.snapshot.paramMap.get("categoryId");
    this.viewCoursesByCategoryId();
  }

  viewCoursesByCategoryId() {
    this.courseService.viewCoursesByCategoryId(this.categoryId).subscribe({
      next: (data: any) => {
        this.courses = data;
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
        this.router.navigate(["/"]);
      }
    });
  }

  viewCourseById(courseId: string) {
    this.router.navigate(["/courses", courseId]);
  }

  addToWishlist(courseId: any) {
    let studentId = Number(window.localStorage.getItem("userId"));
    if (Number(window.localStorage.getItem("userId"))) {
      this.wishlistService.insertCourseIntoWishlist(studentId, courseId).subscribe({
        next: (data: any) => {
          Swal.fire({
            icon: "success",
            title: "Course Added!",
            text: "Course added to wishlist successfully!"
          });
          this.router.navigate(["/wishlist/", Number(window.localStorage.getItem("userId"))]);
        },
        error: (err) => {
          Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
          this.router.navigate(["/wishlist/", Number(window.localStorage.getItem("userId"))]);
        }
      });
    }
    else {
      this.router.navigate(["/login"]);
    }
  }

}
