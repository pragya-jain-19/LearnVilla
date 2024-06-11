import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { Wishlist } from '../../../models/wishlist';
import { CourseService } from '../../../services/course.service';
import { WishlistService } from '../../../services/wishlist.service';

@Component({
  selector: 'app-view-courses',
  standalone: true,
  imports: [],
  templateUrl: './view-courses.component.html',
  styleUrl: './view-courses.component.css'
})
export class ViewCoursesComponent implements OnInit {

  courses: Course[] = [];
  errors: string[] = [];
  constructor(private courseService: CourseService, private router: Router, private wishlistService: WishlistService) { }

  ngOnInit(): void {
    this.viewAllCourses();
  }

  viewAllCourses() {
    this.courseService.viewAllCourses().subscribe((data: Course[]) => {
      this.courses = data;
    })
  }

  viewCourseById(courseId: string) {
    this.router.navigate(["/courses", courseId]);
  }

  addToWishlist(courseId: any) {
    let studentId = Number(window.localStorage.getItem("userId"));
    if (Number(window.localStorage.getItem("userId"))) {
      this.wishlistService.insertCourseIntoWishlist(studentId, courseId).subscribe({
        next: (data: Wishlist) => {
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

