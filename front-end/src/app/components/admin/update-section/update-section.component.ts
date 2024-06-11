import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Action } from 'rxjs/internal/scheduler/Action';
import Swal from 'sweetalert2';
import { Section } from '../../../models/section';
import { SectionService } from '../../../services/section.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-update-section',
  standalone: true,
  imports: [ReactiveFormsModule, SidenavComponent],
  templateUrl: './update-section.component.html',
  styleUrl: './update-section.component.css'
})
export class UpdateSectionComponent {
  sectionId = new FormControl("", [
    Validators.required
  ]);

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

  sectionContent = new FormControl("");

  videoUrl = new FormControl("");

  updateSectionForm = new FormGroup({
    sectionId: this.sectionId,
    courseId: this.courseId,
    sectionNumber: this.sectionNumber,
    sectionName: this.sectionName,
    sectionContent: this.sectionContent,
    videoUrl: this.videoUrl
  });

  id: any;
  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get("sectionId");
    this.viewSectionById(this.id);
  }

  section: Section = {} as Section;
  viewSectionById(sectionId: number) {
    this.sectionService.viewSectionById(sectionId).subscribe({
      next: (data: any) => {
        this.section = data;
        this.updateFormInStarting(this.section, data.courseId);
      }
    });
  }

  updateFormInStarting(section: Section, courseId: string) {
    this.updateSectionForm.patchValue({
      sectionId: section.sectionId?.toString(),
      courseId: courseId,
      sectionNumber: section.sectionNumber?.toString(),
      sectionName: section.sectionName,
      sectionContent: section.sectionContent,
      videoUrl: section.videoUrl
    });
  }

  constructor(private sectionService: SectionService, private router: Router, private route: ActivatedRoute) { }

  updateSection() {
    this.sectionService.updateSection(this.updateSectionForm.value).subscribe({
      next: (data: any) => {
        Swal.fire({
          title: "Updated!",
          text: "Section Updated successfully..!",
          icon: "success"
        });
        this.router.navigate(["/sections-page"]);
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
}
