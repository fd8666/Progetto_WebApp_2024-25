import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import {FooterComponent} from "../footer/footer.component";
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-registrazione',
  standalone: true,
  templateUrl: './registrazione.component.html',
  styleUrls: ['./registrazione.component.css'],
  imports: [CommonModule, FormsModule, HttpClientModule, HeaderComponent, FooterComponent, RouterLink]
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
        next: (response: any) => {
          console.log("Risposta dal backend:", response);
          this.message = response.result || "Registrazione avvenuta con successo!";
          this.messageType = 'success';
        },
        error: (error) => {
          console.error("Errore backend:", error);

          // Estrarre il messaggio di errore dal JSON
          if (error.error && error.error.result) {
            this.message = error.error.result;
          } else {
            this.message = "Si è verificato un errore durante la registrazione.";
          }
          this.messageType = 'danger';
        },
        complete: () => console.log("Richiesta completata!")
      });
  }
}
