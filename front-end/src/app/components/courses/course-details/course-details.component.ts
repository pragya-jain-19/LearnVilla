import { Input } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Course } from '../../../models/course';
import { Section } from '../../../models/section';
import { CourseService } from '../../../services/course.service';
import { SectionService } from '../../../services/section.service';
import { WishlistService } from '../../../services/wishlist.service';
import { HeaderComponent } from '../../header/header.component';

@Component({
  selector: 'app-course-details',
  standalone: true,
  imports: [HeaderComponent],
  templateUrl: './course-details.component.html',
  styleUrl: './course-details.component.css'
})
export class CourseDetailsComponent {

  @Input() courseId : string = this.route.snapshot.paramMap.get("courseId")!;;
  course :Course = {} as Course;
  sections :Section[] = [];
  errors : string[] = [];
  viewAll : boolean = false;
  
  constructor(private courseService : CourseService, private route:ActivatedRoute, private sectionService : SectionService, private router:Router, private wishlistService : WishlistService){}

  ngOnInit() {
    this.viewCourseById(this.courseId);
  }

  viewCourseById(courseId : string) {
    this.courseService.viewCourseById(this.courseId).subscribe({
      next : (data:any) => {
        this.course = data;
      },
      error : (err) => {
        Swal.fire({
        icon: "error",
        title: "Something went wrong!",
        text: err.error.message,
      });
    }
  })
}

  viewAllSections(courseId : string) {
    this.sectionService.viewAllSectionsByCourseId(courseId).subscribe({
      next : (data:any) => {
        this.sections = data;
        this.viewAll = true;
      },
      error : (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
      }
    })
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
