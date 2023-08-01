import { Component, OnInit,Input } from '@angular/core';
import { User } from '../user';
import { FormGroup, FormControl } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';

@Component({
   selector: 'app-login',
   templateUrl: './login.component.html',
   styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userlogin = {

    username:'',

    password:''

  }
  status1=true;
  @Input() user:any=new User('','');
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  constructor(private authService : AuthService, private router : Router,private tokenStorage:TokenStorageService) { }

   ngOnInit() {
    
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    } else {
      this.isLoggedIn = false;
      this.roles = [];
    }
   }

   onClickSubmit() : void {
     //fill the code
     if (!this.user.username) {
      alert('UserName is Required!');
      return;
    }
    
    if (!this.user.password) {
      alert('Password is Required!');
      return;
    }

     this.authService.login(this.user).subscribe(data=>{

      console.log(data);

      this.tokenStorage.saveToken(data.accessToken);

      this.tokenStorage.saveUser(data);

      console.log(data);

      this.isLoginFailed = false;

      this.isLoggedIn = true;

      this.roles = this.tokenStorage.getUser().roles;

      alert('You have successfully logged in as '+this.roles);

      this.router.navigate(['navbar']).then(()=>{

        window.location.reload();

      });
    },
    (error: any) => {
      console.error(error);
      if (error.status === 401) {
        alert('Invalid UserName or Password.');
        this.router.navigate(['']);
        window.location.reload();
      }
    });
      this.status1=false;
     
   }

   logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }
}