import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {AuthService} from './auth.service';

export interface UserProfile {
  id: number;
  username: string;
  email: string;
  bio: string;

  profileImage: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserProfileService {
  private apiUrl = 'http://localhost:8080/api/login/user';

  constructor(private http: HttpClient ,private authService:AuthService) {}

  getUserProfile(email: string): Observable<UserProfile> {
    const headers = { 'Accept': 'application/json' };  // Aggiunto header
    return this.http.get<UserProfile>(`${this.apiUrl}/${email}`, { headers });
  }

}
