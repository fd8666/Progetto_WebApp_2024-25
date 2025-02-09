import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// Interfaccia per il profilo utente
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
  private mockUser: UserProfile = {
    id: 1,
    username: 'DemoUser',
    email: 'demo@example.com',
    bio: ' Sono un utente demo.',
    profileImage: 'people19.png',
  };

  // Simula una chiamata API restituendo i dati con un Observable
  getUserProfile(): Observable<UserProfile> {
    return of(this.mockUser);
  }
}
