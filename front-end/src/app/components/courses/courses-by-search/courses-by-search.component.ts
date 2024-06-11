import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { CourseService } from '../../../services/course.service';
import { WishlistService } from '../../../services/wishlist.service';

@Component({
  selector: 'app-courses-by-search',
  standalone: true,
  imports: [],
  templateUrl: './courses-by-search.component.html',
  styleUrl: './courses-by-search.component.css'
})
export class CoursesBySearchComponent {
  courses: Course[] = [];
  name: any;
  constructor(private courseService: CourseService, private route: ActivatedRoute, private router: Router, private wishlistService: WishlistService) { }

  ngOnInit() {
    this.viewCoursesByNameLike();
  }

  viewCoursesByNameLike() {
    this.name = this.route.snapshot.paramMap.get("name");
    this.courseService.viewCoursesByNameLike(this.name).subscribe({
      next: (data: any) => {
        this.courses = data;
        this.ngOnInit();
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
