import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Section } from '../../../models/section';
import { SectionService } from '../../../services/section.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-sections-page',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './sections-page.component.html',
  styleUrl: './sections-page.component.css'
})
export class SectionsPageComponent {
  sections: Section[] = [];
  constructor(private sectionService: SectionService, private router: Router) { }

  ngOnInit() {
    this.viewAllSections();
  }

  viewAllSections() {
    this.sectionService.viewAllSections().subscribe({
      next: (data: Section[]) => {
        this.sections = data;
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

  deleteSection(sectionId: any) {
    Swal.fire({
      title: "Are you sure you want to delete this section?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.sectionService.deleteSection(sectionId).subscribe({
        next: (data: string) => {
            Swal.fire({
              title: "Deleted!",
              text: data,
              icon: "success"
            });
            this.viewAllSections();
            this.router.navigate(["/enrollments-page"]);
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
}
