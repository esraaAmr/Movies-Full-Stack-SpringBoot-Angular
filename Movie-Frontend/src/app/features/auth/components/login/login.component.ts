import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService, UserDto } from '../../../../core/services/api.service';
import { AuthService, User } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading = false;
  showPassword = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private apiService: ApiService,
    private authService: AuthService
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      rememberMe: [false]
    });
  }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.redirectBasedOnRole();
    }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      const { username, password, rememberMe } = this.loginForm.value;

      this.apiService.login(username, password).subscribe({
        next: (response: UserDto) => {
          const user: User = {
            id: response.id || 0,
            username: response.username,
            password: response.password || '',
            role: response.role || 'USER'
          };
          
          this.authService.setCurrentUser(user);
          
          if (rememberMe) {
            localStorage.setItem('rememberMe', 'true');
          }
          
          
          this.errorMessage = '';
          
          
          this.redirectBasedOnRole();
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Login error:', error);
          this.errorMessage = error.message || 'Login failed. Please check your credentials and try again.';
          this.isLoading = false;
        }
      });
    } else {
      this.markFormGroupTouched();
    }
  }

  private markFormGroupTouched(): void {
    Object.keys(this.loginForm.controls).forEach(key => {
      const control = this.loginForm.get(key);
      control?.markAsTouched();
    });
  }

  getFieldError(fieldName: string): string {
    const field = this.loginForm.get(fieldName);
    if (field?.errors && field.touched) {
      if (field.errors['required']) {
        return `${fieldName.charAt(0).toUpperCase() + fieldName.slice(1)} is required`;
      }
      if (field.errors['minlength']) {
        if (fieldName === 'username') {
          return 'Username must be at least 3 characters long';
        } else {
          return 'Password must be at least 3 characters long';
        }
      }
    }
    return '';
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.loginForm.get(fieldName);
    return !!(field?.invalid && field.touched);
  }

  private redirectBasedOnRole(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      if (user.role === 'ADMIN') {
        this.router.navigate(['/admin-dashboard']);
      } else if (user.role === 'USER') {
        this.router.navigate(['/user-dashboard']);
      } else {
        this.router.navigate(['/user-dashboard']);
      }
    }
  }
}