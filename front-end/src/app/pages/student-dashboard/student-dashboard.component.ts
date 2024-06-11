import { Component } from '@angular/core';
import { StudentNavComponent } from '../../components/student/student-nav/student-nav.component';

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [StudentNavComponent],
  templateUrl: './student-dashboard.component.html',
  styleUrl: './student-dashboard.component.css'
})
export class StudentDashboardComponent {
  email: any;
  ngOnInit() {
    this.email = window.localStorage.getItem("email");
  }
}
