import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  standalone: true,
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
    this.http.post('http://localhost:8080/api/login/password', { email: this.email, password: this.password }, { responseType: 'json' })
      .subscribe({
        next: (response: any) => {
          if (response.result === 'Success') {
            console.log('Login riuscito:', response);
            this.router.navigate([""]);  // Reindirizza alla home o alla dashboard
          }
        },
        error: (error) => {
          console.error('Errore dalla response:', error);

          if (error.error) {
            try {
              //Conversione JSON string
              const errorJson = typeof error.error === 'string' ? JSON.parse(error.error) : error.error;
              this.errorMessage = errorJson.result || 'Errore sconosciuto!';
            } catch (e) {
              this.errorMessage = `Errore server: ${error.status} - ${error.statusText}`;
            }
          } else {
            this.errorMessage = `Errore generico: ${error.message}`;
          }
        }
      });
  }

}
