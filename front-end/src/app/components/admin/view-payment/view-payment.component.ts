import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../../models/course';
import { Payment } from '../../../models/payment';
import { Student } from '../../../models/student';
import { CourseService } from '../../../services/course.service';
import { PaymentService } from '../../../services/payment.service';
import { StudentService } from '../../../services/student.service';
import { SidenavComponent } from '../sidenav/sidenav.component';

@Component({
  selector: 'app-view-payment',
  standalone: true,
  imports: [SidenavComponent],
  templateUrl: './view-payment.component.html',
  styleUrl: './view-payment.component.css'
})
export class ViewPaymentComponent {
  paymentId : any;
  payment :Payment = {} as Payment;
  student : Student = {} as Student;
  courses : Course[] = [];
  constructor(private paymentService : PaymentService, private route:ActivatedRoute, private studentService : StudentService, private courseService : CourseService) {}

ngOnInit() {
  this.paymentId = this.route.snapshot.paramMap.get("paymentId");
  this.viewPaymentById();
}

viewPaymentById() {
  this.paymentService.viewPaymentByPaymentId(this.paymentId).subscribe({
    next : (data : any) => {
      this.payment = data;
      this.student = data.student;
      this.courses = data.courses;
    }
  });
}

}
