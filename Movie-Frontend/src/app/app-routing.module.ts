import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/auth/components/login/login.component';
import { AdminDashboardComponent } from './features/admin/components/dashboard/dashboard.component';
import { UserDashboardComponent } from './features/user/components/dashboard/dashboard.component';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { UserGuard } from './core/guards/user.guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'admin-dashboard', 
    component: AdminDashboardComponent,
    canActivate: [AdminGuard]
  },
  { 
    path: 'user-dashboard', 
    component: UserDashboardComponent,
    canActivate: [UserGuard]
  },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
