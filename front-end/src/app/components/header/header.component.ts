import { FormsModule, NgForm } from '@angular/forms';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [FormsModule, RouterLinkActive, RouterLink, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  categories: Category[] = [];
  userIdExists = false;
  userId: any;
  isLoggedIn = false;
  isStudent = false;
  isAdmin = false;
  searchByName:string="";

  constructor(private categoryService: CategoryService, private router: Router) { }
  ngOnInit() {
    setInterval(() => this.check(), 1000);
    this.viewAllCategories();
  }
  viewAllCategories() {
    this.categoryService.viewAllCategories().subscribe({
      next: (data: Category[]) => {
        this.categories = data;
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message
        });
      }
    });
  }

  check() {
    this.userId = window.localStorage.getItem("userId");
    if (this.userId) {
      this.isLoggedIn = true;
      if (this.userId == 1) {
        this.isAdmin = true;
      }
      else {
        this.isStudent = true;
      }
      return true;
    }
    else {
      this.isLoggedIn = false
      return false;
    }
  }

  logout() {
    this.userId = window.localStorage.getItem("userId");
    localStorage.clear();
    sessionStorage.clear();
    this.isAdmin = false;
    this.isStudent = false;
    this.router.navigate(["/"]);
  }

  findCoursesByName(searchForm : NgForm) {
    this.router.navigate(["/courses/nameLike/", this.searchByName]);
    searchForm.reset();
  }

}
