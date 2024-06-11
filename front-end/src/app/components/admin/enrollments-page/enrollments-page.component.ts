import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Enrollment } from '../../../models/enrollment';
import { EnrollmentService } from '../../../services/enrollment.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-enrollments-page',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './enrollments-page.component.html',
  styleUrl: './enrollments-page.component.css'
})
export class EnrollmentsPageComponent {
  enrollments: Enrollment[] = [];
  constructor(private enrollmentService: EnrollmentService, private router: Router) { }

  ngOnInit() {
    this.viewAllEnrollments();
  }

  viewAllEnrollments() {
    this.enrollmentService.viewAllEnrollments().subscribe({
      next: (data: Enrollment[]) => {
        this.enrollments = data;
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

  deleteEnrollment(enrollmentId: any) {
    Swal.fire({
      title: "Are you sure you want to delete this enrollment?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.enrollmentService.deleteEnrollment(enrollmentId).subscribe({
        next: (data: string) => {
            Swal.fire({
              title: "Deleted!",
              text: data,
              icon: "success"
            });
            this.viewAllEnrollments();
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
