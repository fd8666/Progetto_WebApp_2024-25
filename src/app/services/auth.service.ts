import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/login'; // URL del backend
  logn: boolean = this.getAuthStatus(); // Recupera lo stato salvato
  email: string = this.getEmail();

  constructor(private http: HttpClient) {}

  setUser(email: string): void {
    this.logn = true;
    this.email = email;
    sessionStorage.setItem('email', email);
    sessionStorage.setItem('isAuthenticated', 'true'); // Salva l'autenticazione
  }

  logOut(): void {
    this.logn = false;
    this.email = "";
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('isAuthenticated'); // Rimuove i dati salvati
  }

  login(email: string, password: string): Observable<string> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<string>(`${this.apiUrl}/password`, { email, password }, { headers })
      .pipe(
        catchError(error => {
          let errorMsg = 'Errore sconosciuto';
          if (error.status === 400) {
            errorMsg = error.error; // Il messaggio di errore inviato dal backend
          }
          return throwError(() => new Error(errorMsg));
        })
      );
  }

  private getAuthStatus(): boolean {
    return sessionStorage.getItem('isAuthenticated') === 'true'; // Recupera l'autenticazione salvata
  }

  private getEmail(): string {
    return sessionStorage.getItem('email') || ''; // Recupera l'email salvata
  }
}
