import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {FormsModule} from '@angular/forms';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private authService:AuthService) {}


  async login(email: string, password: string) {
    console.log('Login function called');

    try {
      const response = await firstValueFrom(this.authService.loginWithPassword({ email, password }));
      console.log('Login Success:', response);
    } catch (error) {
      console.error('Login Failed:', error);
    }
  }

}
