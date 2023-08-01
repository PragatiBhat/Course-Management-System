import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  constructor(private tokenStorage:TokenStorageService,private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  } 

}
