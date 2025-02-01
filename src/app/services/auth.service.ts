import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


interface LoginResponce{
  message:string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apriUrl = "http://localhost:8080/api/test/success_post";

  constructor(private http: HttpClient) {}

  loginWithPassword(data: { email: string; password: string }): Observable<string> {
    console.log('Login function called');
    return this.http.post<string>(this.apriUrl, data, { responseType: 'text' as 'json' });
  }

}
