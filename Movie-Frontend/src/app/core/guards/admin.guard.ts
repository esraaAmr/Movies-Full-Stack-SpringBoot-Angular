import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean {
    if (this.authService.isLoggedIn() && this.authService.isAdmin()) {
      return true;
    } else if (this.authService.isLoggedIn() && this.authService.isUser()) {
      // Redirect regular users to their dashboard
      this.router.navigate(['/user-dashboard']);
      return false;
    } else {
      
      this.router.navigate(['/login']);
      return false;
    }
  }
}
