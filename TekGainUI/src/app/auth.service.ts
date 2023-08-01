import { tap, delay } from 'rxjs/operators';
import { Injectable,EventEmitter } from '@angular/core';
import { User } from './user';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpResponse } from '@angular/common/http';
import { observable, Observable, of,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
   providedIn: 'root'
})
export class AuthService { 
   token:any=[];
   logstatus:any = new EventEmitter<string>();
   
   login(user:Object): Observable<any>{
    
      return this.http.post("http://localhost:9098/app/signin",user);
 //  fill the code
   }
   
   getToken():any{
     
 //  fill the code
        return "";
   }
   
     
   logout(): void {
 
      
 //  fill the code
 
   }

   constructor(private http: HttpClient) {
    
     
    }
}