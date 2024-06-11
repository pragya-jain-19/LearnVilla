import { Component } from '@angular/core';
import { ReactiveFormsModule, Validators, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoryService } from '../../../services/category.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-add-category',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent],
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {
  categoryId = new FormControl("", [
    Validators.required,
    Validators.pattern("^[A-Z]{2}$"),
  ]);

  categoryName = new FormControl("", [
    Validators.required,
    Validators.minLength(2)
  ]);

  addCategoryForm = new FormGroup({
    categoryId: this.categoryId,
    categoryName: this.categoryName
  });

  constructor(private categoryService: CategoryService, private router: Router) { }

  addCategory() {
    this.categoryService.addCategory(this.addCategoryForm.value).subscribe({
      next : (data:any) => {
        Swal.fire({
          title: "Added!",
          text: "Category Added successfully..!",
          icon: "success"
        });
        this.router.navigate(["/categories-page"]);
      },
      error : (err) => {
        Swal.fire({
          title: "Something went wrong!",
          text: err.error.message,
          icon: "error"
        });
      }
    });
    
  }
}
