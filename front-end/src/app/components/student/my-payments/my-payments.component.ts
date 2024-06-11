import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Payment } from '../../../models/payment';
import { PaymentService } from '../../../services/payment.service';
import { StudentNavComponent } from '../student-nav/student-nav.component';

@Component({
  selector: 'app-my-payments',
  standalone: true,
  imports: [StudentNavComponent],
  templateUrl: './my-payments.component.html',
  styleUrl: './my-payments.component.css'
})
export class MyPaymentsComponent {
  payments: Payment[] = [];
  courseIds: string = "";
  studentId : any;
  constructor(private paymentService: PaymentService, private route:ActivatedRoute) { }

  ngOnInit() {
    this.viewPaymentsByStudentId();
  }

  viewPaymentsByStudentId() {
    this.studentId = this.route.snapshot.paramMap.get("studentId");
    this.paymentService.viewPaymentsByStudentId(this.studentId).subscribe({
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
          text: err.error.message
        });
      }
    });
  }
}
