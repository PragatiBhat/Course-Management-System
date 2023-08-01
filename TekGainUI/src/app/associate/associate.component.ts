import { Component, OnInit, Input,  } from '@angular/core';
import {Router} from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AssociateService } from '../associate.service';
import { Associate } from '../associate';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-associate',
  templateUrl: './associate.component.html',
  styleUrls: ['./associate.component.css']
})
export class AssociateComponent implements OnInit {

  associateId:string='';
  associateName:string='';
  associateAddress:string='';
  associateEmailId:string='';

  private roles: string[] = [];
  isLoggedIn = false;
  add = false;
  update = false;
  view = false;
  show=false;
  showAll=false;
  errorFlag= false;
   message:string=''; 
   error:string='';

 
 @Input() associate:any=new Associate('','','',''); 
 associateModel:any=new Associate('','','',''); 

   
 // instructorNames =[];
 associates: Array<any>=[];
 associatesById: Array<any>=[];
 paramFlag=1;
 sub:any="";

 @Input()title:string='';

 ngOnInit() {

   
 //  fill the code
 this._Activatedroute.queryParams.subscribe(params => {
  this.paramFlag = +params['paramFlag'];
});

this.associateService.viewAssociates().subscribe(data=>
  {
    this.associates=data;
  });

  this.isLoggedIn = !!this.tokenStorage.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      this.roles = user.roles;

      this.add = this.roles.includes('ROLE_ADMIN');
      this.update = this.roles.includes('ROLE_USER');
      this.view = this.roles.includes('ROLE_USER');
      this.show= this.roles.includes('ROLE_ADMIN');

    }
}


ngOnDestroy() {
 
 //  fill the code
 
}

 constructor(private associateService: AssociateService,private router: Router,private _Activatedroute:ActivatedRoute,private tokenStorage:TokenStorageService) { }
 
 
 addAssociate() : void {
 
 //  fill the code
 this.associateModel=new Associate(this.associateId,this.associateName,this.associateEmailId,this.associateAddress);
      this.associateService.addAssociate(this.associateModel).subscribe(data=>
        {
          this.associate=data;
        });
 
 }


updateCourse() : void {

 //  fill the code
 this.associateService.updateAssociate(this.associateId,this.associateAddress).subscribe(data=>
  {this.associate=data},
  (error) => {
    this.errorFlag=true;
    console.error(error);
  }
  );
  if(this.associate!=null)
  {
    this.message="Address updated Successfully";
  }
  
 }

 viewByAssociateId() : void {
 
  this.associateService.viewByAssociateId(this.associateId).subscribe(
     (response: any) => {
        console.log(response);
  
        const associate: Associate = new Associate(
          response.associateId,
          response.associateName,
          response.associateAddress,
          response.associateEmailId,
         
        );
  
        this.associatesById = [associate];
        this.view = true;
        this.errorFlag = false;
         
      },
      (error) => {
        console.error(error);
        this.errorFlag = true;
      }
    );
    }


 viewAssociates() : void {  
  
 //  fill the code
 this.associateService.viewAssociates().subscribe(
  (response: any) => {
      console.log(response);
      this.show=true;
      this.associatesById=response;
    },
    (error) => {
      this.errorFlag=true;
      console.error(error);
    }
    );
 }

 logout(): void {
  this.tokenStorage.signOut();
  this.router.navigate(['/login']);
} 

 

}

