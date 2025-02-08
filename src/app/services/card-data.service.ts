import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  private apiUrl = 'http://localhost:8080/api/cards'; // Assumendo che il backend giri sulla porta 8080

  constructor(private http: HttpClient) {}

  getCardById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/getCard/${id}`);
  }
}
