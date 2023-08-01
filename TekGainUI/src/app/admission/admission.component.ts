import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../token-storage.service';
import { AdmissionService } from '../admission.service';
import { Admission } from '../admission';

@Component({
  selector: 'app-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.css']
})
export class AdmissionComponent implements OnInit {

   registrationId:string='';	
   courseId	:string='';
   associateId:string=''	;
   fees:number=0;	
   feedback	:string='';
   returnfees:any='';
   message:string=''; 
   error:string=''; 
   rating:number=0;
   returnCourseId:any='';
   paymentPrice:any='';
   finalfees:number=0;
   regiNo:number=0;
   returnmessage:any='';
   url:string='';
    errorFlag=false;
    
  @Input() admission:any=new Admission('','','',0,'',0); 
  admissionModel:any=new Admission('','','',0,'',0); 
   
 
    admissions: Array<any>=[];
    feedbackArray:Array<Admission> = []; 
    AdmissionsArray= []; 
    paramFlag=1;
    sub:any="";
    admissionById:Array<Admission> = [];

  private roles: string[] = [];
  isLoggedIn = false;
  add = false;
  calculate = false;
  addFedback = false;
  highest=false;
  viewFeedback=false;
  showAll=false;
  payment=false;

  @Input()title:string='';

 ngOnInit() {
  
  this._Activatedroute.queryParams.subscribe(params => {
    this.paramFlag = +params['paramFlag'];
  });

  // this.admissionService.viewAllRegistrations().subscribe(data=>{
  //   this.admissionA=data;
  // });

   this.isLoggedIn = !!this.tokenStorage.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      this.roles = user.roles;

      this.add = this.roles.includes('ROLE_USER');
      this.calculate = this.roles.includes('ROLE_ADMIN');
      this.addFedback = this.roles.includes('ROLE_USER');
      this.highest = this.roles.includes('ROLE_ADMIN');
      this.viewFeedback = this.roles.includes('ROLE_USER');
      this.showAll = this.roles.includes('ROLE_ADMIN');
      this.payment=this.roles.includes('ROLE_USER');
    }
}

ngOnDestroy() {
 //  this.sub.unsubscribe();
  
}

 constructor(private admissionService: AdmissionService,private router: Router,private _Activatedroute:ActivatedRoute,private tokenStorage:TokenStorageService) { }
 

 registration() : void {
 
//  fill the code
this.admissionModel=new Admission(this.registrationId,this.courseId,this.associateId,this.fees,this.feedback,this.rating);
this.admissionService.registration(this.admissionModel,this.associateId,this.courseId).subscribe(data=>
  this.admission=data);

 }

 totalFees(){
 
 //  fill the code
 this.admissionService.calculateFees(this.associateId).subscribe(data=>
  {
    this.returnfees=data;
  });
  if(this.returnfees!='')
  {
    this.message="Total Fees:"+ this.returnfees;
  }
 }

 addFeedback() : void {
 
 //  fill the code
 
 }

 highestFee() : void {  
  
 //  fill the code
 this.admissionService.highestFeeForTheRegisteredCourse(this.associateId).subscribe(data=>
  {
    this.returnCourseId=data;
  });
  if(this.returnCourseId!='')
  {
    this.message="Course having Highest Fee:"+this.returnCourseId;
  }
 
 }

 viewFeedbackByCourseId( ){
 
 //  fill the code
 this.admissionService.viewFeedbackByCourseId(this.courseId).subscribe(data=>
  {
    this.admission=data;
    console.log(this.admission);
  })
 
 }
 viewAll(){
  this.admissionService.viewAllRegistrations().subscribe(
   (response: any) => {
       console.log(response);
       this.showAll=true;
       this.admissionById=response;
     },
     (error) => {
       this.errorFlag=true;
       console.error(error);
     }
     );
   
 }
getFees()
{
  this.admissionService.getFees(this.registrationId).subscribe((response:any)=>{
    this.admission=response;
    this.finalfees=this.admission.fees;
    this.regiNo=this.admission.registrationId;
    this.paymentPrice="Payment Price is:"+this.finalfees;
    
  })
}
 makePayment(){
    this.admissionService.makePayment(this.regiNo,this.finalfees).subscribe((response:any)=>
    {
      this.returnmessage=response;
      // console.log(this.returnmessage);
      this.url=this.returnmessage[0];
      alert("Payment page is opening");
      window.open(this.url, '_blank');
      
    });
    
 }

 logout(): void {
  this.tokenStorage.signOut();
  this.router.navigate(['/login']);
} 
}

