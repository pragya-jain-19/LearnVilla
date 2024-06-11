import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SectionService } from '../../../services/section.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-add-section',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent
  ],
  templateUrl: './add-section.component.html',
  styleUrl: './add-section.component.css'
})
export class AddSectionComponent {
  courseId = new FormControl("", [
    Validators.required,
    Validators.pattern("^[A-Z]{2}[0-9]{4}$")
  ]);

  sectionNumber = new FormControl("", [
    Validators.required
  ]);

  sectionName = new FormControl("", [
    Validators.required,
    Validators.min(5)
  ]);

  sectionContent = new FormControl("", [
    Validators.required
  ]);

  videoUrl = new FormControl("");

  addSectionForm = new FormGroup({
    courseId: this.courseId,
    sectionNumber: this.sectionNumber,
    sectionName: this.sectionName,
    sectionContent: this.sectionContent,
    videoUrl: this.videoUrl
  });

  errors: string[] = []
  constructor(private sectionService: SectionService, private router: Router) { }

  addSection() {
    this.sectionService.addSection(this.addSectionForm.value).subscribe({
      next: (data: any) => {
        Swal.fire({
          title: "Added!",
          text: "Section Added successfully..!",
          icon: "success"
        });
        this.router.navigate(["/sections-page"]);
      },
      error: (err) => {
        Swal.fire({
          title: "Something went wrong!",
          text: err.error.message,
          icon: "error"
        });
      }
    });
  }

}
