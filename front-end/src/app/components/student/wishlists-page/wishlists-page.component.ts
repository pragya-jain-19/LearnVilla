import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Wishlist } from '../../../models/wishlist';
import { CourseService } from '../../../services/course.service';
import { WishlistService } from '../../../services/wishlist.service';
import { StudentNavComponent } from '../student-nav/student-nav.component';

@Component({
  selector: 'app-wishlists-page',
  standalone: true,
  imports: [StudentNavComponent, RouterLink, RouterLinkActive],
  templateUrl: './wishlists-page.component.html',
  styleUrl: './wishlists-page.component.css'
})
export class WishlistsPageComponent {
  errors: string[] = [];
  wishlist : Wishlist = {} as Wishlist;
  courses: any;
  student : any;
  email : any;
  id : any = Number(window.localStorage.getItem("userId"));
  constructor(private wishlistService: WishlistService, private courseService: CourseService, private router: Router) { }

  ngOnInit() {
    this.email = window.localStorage.getItem("email");
    this.wishlist.price = 0;
    this.viewWishlistByStudentId(Number(window.localStorage.getItem("userId")));
  }

  viewWishlistByStudentId(studentId: number) {
    this.wishlistService.viewWishlistByStudentId(studentId).subscribe({
      next: (data: Wishlist) => {
        this.wishlist = data;
        this.student = data.student;
        this.courses = data.courses;
      }
    });   
  }

  deleteFromWishlist(courseId: string) {
    this.wishlistService.deleteCourseFromWishlist(this.id, courseId).subscribe({
      next : (data:any) => {
        this.courses = data;
        this.wishlist.price = 0;
        this.viewWishlistByStudentId(this.id);
      }
    })
  }

  proceedToPay() {
    let courseIds = "";
    this.courses.forEach((course:any) => {
      courseIds += course.courseId+" ";
    });
    window.localStorage.setItem("courseIds", `${courseIds}`);
    window.localStorage.setItem("price", this.wishlist.price+"");
    this.router.navigate(["/add-payment"]);
  }

  deleteWishlist() {
    this.wishlistService.deleteWishlistByStudentId(this.id).subscribe({
      next : (data:any) => {
        this.courses = [];
        this.wishlist.price = 0;
        this.viewWishlistByStudentId(this.id);
      }
    })
  }

}
