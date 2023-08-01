import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// import { JwtHelperService } from '@auth0/angular-jwt';
// import { JwtModule } from "@auth0/angular-jwt";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule,} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import { Routes, RouterModule } from '@angular/router';

import {MatCardModule} from '@angular/material/card'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatTableModule} from '@angular/material/table';
import {FormsModule} from '@angular/forms';
import {  ReactiveFormsModule } from '@angular/forms';  
import { HttpClientModule } from '@angular/common/http';

import { CourseComponent } from './course/course.component';
import { AdmissionComponent } from './admission/admission.component';
import { AssociateComponent } from './associate/associate.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SuccessComponent } from './success/success.component';
import { FailureComponent } from './failure/failure.component';
export const routes: Routes = [
 
// Fill code for routing
  { path: 'course', component: CourseComponent },
  { path: 'admission', component: AdmissionComponent },
  { path: 'associate' ,component: AssociateComponent},
  { path: '',component:LoginComponent},
  {path:'navbar',component:NavbarComponent},
  {path:'login',component:LoginComponent},
  {path:'success',component:SuccessComponent},
  {path:'cancel',component:FailureComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    CourseComponent,    
    AdmissionComponent,
    AssociateComponent,
    LoginComponent,
    NavbarComponent,
    SuccessComponent,
    FailureComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,FormsModule,ReactiveFormsModule,HttpClientModule,
    BrowserAnimationsModule,MatFormFieldModule,MatInputModule,MatProgressSpinnerModule,MatTableModule,
	MatFormFieldModule,MatInputModule,MatProgressSpinnerModule,MatTableModule,
    MatButtonModule,MatToolbarModule,MatCardModule,  MatButtonModule, MatCardModule, 
    MatIconModule,MatSidenavModule,MatListModule,RouterModule.forRoot(routes),MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
