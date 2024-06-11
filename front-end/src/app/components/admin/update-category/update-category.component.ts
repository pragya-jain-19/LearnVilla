import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CategoryService } from '../../../services/category.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-update-category',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent],
  templateUrl: './update-category.component.html',
  styleUrl: './update-category.component.css'
})
export class UpdateCategoryComponent {
  categoryId = new FormControl("", [
    Validators.required,
    Validators.pattern("^[A-Z]{2}$"),
  ]);

  categoryName = new FormControl("", [
    Validators.required,
    Validators.minLength(2)
  ]);

  updateCategoryForm = new FormGroup({
    categoryId: this.categoryId,
    categoryName: this.categoryName
  });

  id: any;
  tempName : any;
  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get("categoryId");
    this.tempName = window.localStorage.getItem("categoryName");
    this.updateFormInStarting(this.id, this.tempName);
  }

  updateFormInStarting(catId : string, catName : string) {
    this.updateCategoryForm.patchValue({
      categoryId: catId,
      categoryName: catName
    })
  }

  constructor(private categoryService: CategoryService, private router: Router, private route: ActivatedRoute) { }

  updateCategory() {
    this.updateCategoryForm.value.categoryId = this.id;
    this.categoryService.updateCategory(this.updateCategoryForm.value).subscribe({
      next: (data: any) => {
        Swal.fire({
          title: "Updated!",
          text: "Category Updated successfully..!",
          icon: "success"
        });
        this.router.navigate(["/categories-page"]);
      },
      error: (err) => {
        Swal.fire({
          icon: "error",
          title: "Something went wrong!",
          text: err.error.message,
        });
      }
    }
    );
  }
}
