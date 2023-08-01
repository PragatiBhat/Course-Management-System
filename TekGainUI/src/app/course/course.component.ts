import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from '../course.service';
import { Course } from '../course';
import { TokenStorageService } from '../token-storage.service';
import {MatSelectModule} from '@angular/material/select';


@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  courseId:any='';
  courseName:any='';
  fees:number=0;
  rating:number=0;
  duration:number=0;
  courseType:string='';
  errorFlag= false;
  message:string='';
  instructorNames:string='';
  error:string='';
  
  private roles: string[] = [];
  isLoggedIn = false;
  add = false;
  update = false;
  view = false;
  showrating = false;
  deactivate = false;
  viewAll = false;

  @Input() course:any=new Course('','',0,0,'',0); 
  courseModel:any=new Course('','',0,0,'',0);     
  
  courses: Array<any>=[];
  courseById:Array<Course> = []; 
  ratings:Array<Course> = [];  
 
  flag1=0;
  flag2=0;
  paramFlag=1;
  sub:any="";

  @Input()title:string='';


  ngOnInit() {
 
    this._Activatedroute.queryParams.subscribe(params => {
      this.paramFlag = +params['paramFlag'];
    });

    this.courseService.viewAllCourses().subscribe(data=>{
      this.courses=data;
    });

    
    this.isLoggedIn = !!this.tokenStorage.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      this.roles = user.roles;

      this.add = this.roles.includes('ROLE_ADMIN');
      this.update = this.roles.includes('ROLE_ADMIN');
      this.view = this.roles.includes('ROLE_USER');
      this.showrating = this.roles.includes('ROLE_ADMIN');
      this.deactivate = this.roles.includes('ROLE_ADMIN');
      this.viewAll = this.roles.includes('ROLE_ADMIN'||'ROLE_USER');
    }
        
    
 //  fill the code
 
 }

 ngOnDestroy() {
  
 //  fill the code
 
 }
 
  constructor(private courseService: CourseService,private router: Router,private _Activatedroute:ActivatedRoute,private tokenStorage:TokenStorageService) { }
  
   addCourse() : void {
 
 //  fill the code
      this.courseModel=new Course(this.courseId,this.courseName,this.fees,this.duration,this.courseType,this.rating);
      this.courseService.addCourse(this.courseModel).subscribe(data=>
        {
          alert("Course added successfully");
          this.course=data;
        },
        (error) => {
          console.error(error);
          this.errorFlag = true;
        }
        );
  }
 

updateCourse() : void {
 
 //  fill the code
 this.courseService.updateCourse(this.courseId,this.fees).subscribe(data=>{
  this.course=data;
 },
 (error) => {
  console.error(error);
  this.errorFlag = true;
});
 if(this.course.courseName!='')
 {
  this.flag2=1;
  this.message="Course updated Successfully";
 }
  }

  
  viewCourseById() : void {  
   
 //  fill the 
//  this.courseService.viewCourseById(this.courseId).subscribe(data=>{
//   this.course=data;
//  });

    for(let i=0;i<this.courses.length;i++)
    {
      if(this.courses[i].courseId==this.courseId)
      {
        this.courseById.push(this.courses[i]);
      }
    }
//  this.courseById.push(this.course);
  }

  viewRatings(){
  
 //  fill the code
 for(let i=0;i<this.courses.length;i++)
 {
   if(this.courses[i].courseId==this.courseId)
   {
     this.ratings.push(this.courses[i]);
   }
 }
  }

  disableCourse() : void {
    
 //  fill the code
      this.courseService.disableCourse(this.courseId).subscribe(data=>{
        this.course=data;
        this.flag1=1;
      },
      (error) => {
        console.error(error);
        this.errorFlag=true;
        this.flag1=0;
      });
      // if(this.course!==null)
      // {
      //     this.flag1=1;
      // }

    
  }

  viewAllCourses():void{
    this.courseService.viewAllCourses().subscribe(data=>
      {
        this.courses=data;
      })
  }
  logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  } 

}
