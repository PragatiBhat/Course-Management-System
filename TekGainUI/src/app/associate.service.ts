
import { Associate } from './associate';
import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpInterceptor } from '@angular/common/http';
import { observable, Observable,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class AssociateService {
  host:string='';
  token:any="";
  returnMsg:any='';
  ASSOCIATE_API = 'http://localhost:9092/associate/';
  constructor(private http: HttpClient,private authService:AuthService) { }

  addAssociate(associate:Object):Observable<Object>{
   
    
 //  fill the code
 return this.http.post(this.ASSOCIATE_API+"addAssociate",associate);
   }
 
  updateAssociate(AssociateId:any,address:any):Observable<Object>{
   
 //  fill the code
   return this.http.put(this.ASSOCIATE_API+`updateAssociate/${AssociateId}/${address}`,AssociateId,address);
  }  

  viewAssociates(): Observable<any> {
   
 //  fill the code
      return this.http.get(this.ASSOCIATE_API+"viewAll");
    }

    viewByAssociateId(associateId:any){
      return this.http.get(this.ASSOCIATE_API+`viewByAssociateId/${associateId}`,associateId);
       
     //  fill the code
          
        }
}
