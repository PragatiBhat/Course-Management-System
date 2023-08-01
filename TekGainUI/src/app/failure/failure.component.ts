import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-failure',
  templateUrl: './failure.component.html',
  styleUrls: ['./failure.component.css']
})
export class FailureComponent implements OnInit {

  constructor(private tokenStorage:TokenStorageService,private router: Router) { }

  ngOnInit(): void {
  }
  logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  } 

}
