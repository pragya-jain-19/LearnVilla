import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/category.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-categories-page',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './categories-page.component.html',
  styleUrl: './categories-page.component.css'
})
export class CategoriesPageComponent {
  categories: Category[] = [];
  constructor(private categoryService: CategoryService, private router:Router) { }

  ngOnInit() {
    this.viewAllCategories();
  }

  viewAllCategories() {
    this.categoryService.viewAllCategories().subscribe((data: Category[]) => {
      this.categories = data;
    })
  }

  deleteCategory(categoryId: string) {
    Swal.fire({
      title: "Are you sure you want to delete this category?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoryService.deleteCategory(categoryId).subscribe({
          next : (data:string) =>{
            Swal.fire({
              title: "Deleted!",
              text: data,
              icon: "success"
            });
            this.viewAllCategories();
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

  updateCategory(categoryId : any, categoryName : any) {
    window.localStorage.setItem("categoryName", categoryName);
    this.router.navigate(["/categories/update-category/", categoryId]);
  }
}
