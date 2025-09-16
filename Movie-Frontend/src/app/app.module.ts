import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { AdminDashboardComponent } from './features/admin/components/dashboard/dashboard.component';
import { UserDashboardComponent } from './features/user/components/dashboard/dashboard.component';
import { DialogComponent } from './shared/components/dialog/dialog.component';
import { LoadingComponent } from './shared/components/loading/loading.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminDashboardComponent,
    UserDashboardComponent,
    DialogComponent,
    LoadingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
