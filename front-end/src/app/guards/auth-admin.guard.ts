import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import Swal from 'sweetalert2';

export const authAdminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userId = Number(window.localStorage.getItem("userId"));
  if (userId === 1) {
    return true;
  }
  else {
    Swal.fire({
      icon: "error",
      title: "Unauthorized access!",
      text: "Please login with valid credentials to access Admin Dashboard!"
    });
    router.navigate(["/login"]);
    return false;
  }
};
