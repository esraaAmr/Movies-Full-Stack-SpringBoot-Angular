import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface User {
  id: number;
  username: string;
  password: string;
  role: 'ADMIN' | 'USER';
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor() {
    this.loadStoredUser();
  }

  private loadStoredUser(): void {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        this.currentUserSubject.next(user);
      } catch (error) {
        console.error('Error parsing stored user data:', error);
        this.clearUserData();
      }
    }
  }

  setCurrentUser(user: User): void {
    this.currentUserSubject.next(user);
    localStorage.setItem('currentUser', JSON.stringify(user));
    localStorage.setItem('authToken', 'logged-in');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null && localStorage.getItem('authToken') === 'logged-in';
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'ADMIN';
  }

  isUser(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'USER';
  }

  logout(): void {
    this.clearUserData();
  }

  private clearUserData(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
    localStorage.removeItem('authToken');
    localStorage.removeItem('rememberMe');
  }
}
