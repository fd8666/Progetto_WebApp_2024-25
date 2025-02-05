import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/login'; // URL del backend

  constructor(private http: HttpClient) {}

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
}
