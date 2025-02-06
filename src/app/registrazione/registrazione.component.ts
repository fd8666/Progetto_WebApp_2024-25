import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-registrazione',
  standalone: true,
  templateUrl: './registrazione.component.html',
  styleUrls: ['./registrazione.component.css'],
  imports: [CommonModule, FormsModule, HttpClientModule, HeaderComponent]
})

export class RegistrazioneComponent {
  username: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  message: string = '';
  messageType: 'success' | 'danger' = 'success';

  constructor(private http: HttpClient) {}

  register() {
    if (this.password !== this.confirmPassword) {
      this.message = 'Le password non coincidono!';
      this.messageType = 'danger';
      return;
    }

    const requestData = {
      username: this.username,
      email: this.email,
      password: this.password
    };

    console.log('Dati inviati:', JSON.stringify(requestData));

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post("http://localhost:8080/api/register/password", requestData, { headers })
      .subscribe({
        next: (response) => console.log("Risposta dal backend:", response),
        error: (error) => console.error("Errore backend:", error),
        complete: () => console.log("Richiesta completata!")
      });

  }



}

