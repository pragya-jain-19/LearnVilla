import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AppConstants } from '../constants/app.constants';
import { Payment } from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  constructor(private http: HttpClient) { }

  addPayment(payment: any) {
    return this.http.post<Payment>(environment.rootUrl + AppConstants.ADD_PAYMENT_API_URL, payment);
  }

  viewAllPayments() {
    return this.http.get<Payment[]>(environment.rootUrl + AppConstants.VIEW_ALL_PAYMENTS_API_URL);
  }

  viewPaymentsByStudentId(studentId: number) {
    return this.http.get<Payment[]>(environment.rootUrl + AppConstants.VIEW_PAYMENTS_BY_STUDENT_ID_API_URL + `${studentId}`);
  }

  viewPaymentByPaymentId(paymentId: number) {
    return this.http.get<Payment>(environment.rootUrl + AppConstants.VIEW_PAYMENT_BY_PAYMENT_ID_API_URL + `${paymentId}`);
  }

  updatePaymentStatus(paymentId: number, status: boolean) {
    return this.http.put<Payment>(environment.rootUrl + AppConstants.UPDATE_PAYMENT_STATUS + `${paymentId}/${status}`, {});
  }

}
