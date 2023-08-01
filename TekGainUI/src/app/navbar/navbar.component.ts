import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private tokenStorage:TokenStorageService,private router : Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  } 
}
