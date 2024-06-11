import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import Swal from 'sweetalert2';
import { Payment } from '../../../models/payment';
import { PaymentService } from '../../../services/payment.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-payments-page',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, SidenavComponent],
  templateUrl: './payments-page.component.html',
  styleUrl: './payments-page.component.css'
})
export class PaymentsPageComponent {
  payments: Payment[] = [];
  courseIds: string = "";
  constructor(private paymentService: PaymentService, private router: Router) { }

  ngOnInit() {
    this.viewAllPayments();
  }

  viewAllPayments() {
    this.paymentService.viewAllPayments().subscribe({
      next: (data: any) => {
        this.payments = data;
        for (let i = 0; i < data.length; i++) {
          this.payments[i].studentId = data[i].student.studentId;
          this.payments[i].courseIds = [];
          data[i].courses.forEach((c: any) => {
            this.payments[i].courseIds?.push(c.courseId);
          });
        }
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
