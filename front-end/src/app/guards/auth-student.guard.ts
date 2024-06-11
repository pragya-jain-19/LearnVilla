import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import Swal from 'sweetalert2';

export const authStudentGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userId = Number(window.localStorage.getItem("userId"));
  if (userId && userId !== 1) {
    return true;
  }
  else {
    Swal.fire({
      icon: "error",
      title: "Unauthorized access!",
      text: "Please login with valid credentials to access Student Dashboard!"
    });
    router.navigate(["/login"]);
    return false;
  }
};
