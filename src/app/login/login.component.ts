import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-login',
  standalone: true,  // <--- Standalone Component
  imports: [CommonModule, FormsModule, HttpClientModule, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  constructor(private http: HttpClient, private router: Router) {}
  login() {
    this.http.post('http://localhost:8080/api/test/success_post', { email: this.email, password: this.password })
      .subscribe({
        next: (response) => {
          console.log('Login riuscito:', response);
          this.router.navigate([""])
        },
        error: (error) => {
          this.errorMessage = 'Credenziali errate o server non disponibile';
        }
      });
  }
}

