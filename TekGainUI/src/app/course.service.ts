import { Injectable } from '@angular/core';
import { HttpClient,HttpErrorResponse,HttpHeaders,HttpInterceptor } from '@angular/common/http';
import { observable, Observable,throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class CourseService {

  host:string='';
  token:any="";
  returnMsg:any='';
  COURSE_API = 'http://localhost:9091/course/';

  ngOnInit() {
    
  }

  constructor(private http: HttpClient,private authService:AuthService) {
  }
  addCourse(course:Object):Observable<Object>{
    console.log(course);
  return this.http.post(this.COURSE_API+"addCourse",course);
 //  fill the code
  }
 
  updateCourse(courseId:any,fees:any):Observable<Object>{
  
 //  fill the code
 return this.http.put(this.COURSE_API+`update/${courseId}/${fees}`,courseId,fees);
   }  

   viewCourseById(courseId:string):Observable<Object>
   {
    return this.http.get(this.COURSE_API+"viewByCourseId/"+courseId);
   }

   viewRatings(courseId:string):Observable<Object>
   {
      return this.http.get(this.COURSE_API+"viewFeedback/"+courseId);
   }
  viewAllCourses(): Observable<any> {
   
   return this.http.get(this.COURSE_API+"viewAll");
 //  fill the code
       
    }

disableCourse(courseId:string):Observable<Object>{
  
 //  fill the code
 return this.http.delete(this.COURSE_API+"deactivate/"+courseId)
}


   

}
