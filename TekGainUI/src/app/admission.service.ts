import { Injectable } from '@angular/core';

import { HttpClient,HttpErrorResponse,HttpHeaders,HttpInterceptor } from '@angular/common/http';
import { observable, Observable,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})

export class AdmissionService {
returnMsg:any='';
Admission_URL="http://localhost:9093/admission/";
  constructor(private http: HttpClient,private authService:AuthService) { }


  registration(admission:Object,associateId:string,courseId:string):Observable<Object>{
   
 //  fill the code
return this.http.post(this.Admission_URL+"register/"+associateId+"/"+courseId,admission);
  }

  calculateFees( associateId:String){
     
 //  fill the code
 
 return this.http.put(this.Admission_URL+associateId,associateId);
  }

  addFeedback( regNo:string,  feedback:string,feedbackRating:any) {

   this.http.post(this.Admission_URL+`feedback/${regNo}/${feedback}/${feedbackRating}`,regNo);
 //  fill the code
return this.returnMsg;
   }

  highestFeeForTheRegisteredCourse(associateId:string){

return this.http.get(this.Admission_URL+"highestFee/"+associateId);
 //  fill the code
  }

 viewFeedbackByCourseId( courseId:string){


 //  fill the code
 return this.http.get(this.Admission_URL+"viewFeedbackByCourseId/"+courseId);
 
 }
 
 getFees(registrationId:any)
 {
    return this.http.get(this.Admission_URL+"getDetails/"+registrationId);
 }
 viewAllAdmissions(): Observable<any> {
   
     
 //  fill the code
return this.http.get(this.Admission_URL+"viewAll");
  }

  makePayment(registrationId:any,fees:any): Observable<any>
  {
    return this.http.post<any>(this.Admission_URL+`makePayment/${registrationId}/${fees}`,registrationId,fees);
  }
   
  viewAllRegistrations()
  {
    return this.http.get(this.Admission_URL+"viewAll");
  }
  
}
