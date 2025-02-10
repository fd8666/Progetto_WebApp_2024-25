import {Component, OnInit, Renderer2} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  isAuthenticated: boolean = false; // Inizializza la variabile
  constructor(private renderer: Renderer2 ,private authService:AuthService,private router:Router) {}

  logout(){
    this.authService.logOut();
    this.router.navigate(['/Login']); // Reindirizza alla pagina di login
  }
  ngOnInit() {
    this.isAuthenticated = this.authService.logn;
  }

}
