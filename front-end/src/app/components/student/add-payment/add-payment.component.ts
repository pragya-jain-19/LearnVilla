import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { EnrollmentService } from '../../../services/enrollment.service';
import { PaymentService } from '../../../services/payment.service';
import { WishlistService } from '../../../services/wishlist.service';

@Component({
  selector: 'app-add-payment',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-payment.component.html',
  styleUrl: './add-payment.component.css'
})
export class AddPaymentComponent {
  price : number = -1;
  gst : number = -1;
  totalPrice : number = -1;
  coursesQuantity : number = -1;
  payment = {
    studentId: window.localStorage.getItem("userId"),
    courseIds: window.localStorage.getItem("courseIds")?.trim().split(" "),
    status: true
  };

  constructor(private paymentService: PaymentService, private router: Router, private wishlistService: WishlistService, private enrollmentService: EnrollmentService) { 
    this.price = Number(window.localStorage.getItem("price"));
    this.coursesQuantity = Number(window.localStorage.getItem("courseIds")?.trim().split(" ").length);
    this.gst = this.coursesQuantity * 18;
    this.totalPrice = this.price + this.gst;
  }

  addPayment() {
    this.paymentService.addPayment(this.payment).subscribe({
      next: (data: any) => {
        this.payment = data;
        if (this.payment.status === true) {
          window.localStorage.removeItem("courseIds");
          for (let i = 0; i < data.courseIds.length; i++) {
            this.addEnrollment({ studentId: this.payment.studentId, courseId: data.courseIds[i] });
          }
          Swal.fire({
            icon : "success",
            title : "Enrollment Successful..!",
            text : "You are enrolled in courses successfully..!"
          });
          this.router.navigate(["/my-enrollments", this.payment.studentId]);
        }
      },
      error: (err) => {
        Swal.fire({
          title: "Something went wrong!",
          text: err.error.message,
          icon: "error"
        });
        this.router.navigate(["/my-enrollments", this.payment.studentId]);
      }
    });
  }

  addEnrollment(enrollment: { studentId: any, courseId: any }) {
    this.enrollmentService.addEnrollment(enrollment).subscribe({
      next: (data: any) => {

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
