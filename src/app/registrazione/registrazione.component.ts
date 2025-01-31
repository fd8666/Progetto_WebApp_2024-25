import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-registrazione',
  imports: [CommonModule,RouterModule],
  templateUrl: './registrazione.component.html',
  standalone: true,
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {}
