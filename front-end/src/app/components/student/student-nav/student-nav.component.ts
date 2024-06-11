import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-student-nav',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './student-nav.component.html',
  styleUrl: './student-nav.component.css'
})
export class StudentNavComponent {
  userId = window.localStorage.getItem("userId");
}
