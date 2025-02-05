import {Component, Inject, OnInit} from '@angular/core';
import { UserProfileService, UserProfile } from '../services/user-profile.service';
import {FormsModule} from '@angular/forms';
import {NgFor, NgIf} from '@angular/common';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
  imports: [NgIf, NgFor, FormsModule, HeaderComponent],
  standalone: true
})

export class ProfilePageComponent implements OnInit {
  userProfile!: UserProfile;
  showChat = false; // Per mostrare/nascondere la chat
  editableUser: UserProfile = {} as UserProfile;
  isEditing = false;
  previewImage: string | ArrayBuffer | null = null;

  constructor(@Inject(UserProfileService) private userProfileService: UserProfileService) {}

  // Metodo per gestire la selezione del file
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewImage = reader.result; // Salva il risultato della lettura come stringa base64
      };

      reader.readAsDataURL(file); // Leggi il file come URL data
    }
  }
  ngOnInit(): void {
    this.userProfileService.getUserProfile().subscribe(user => {
      this.userProfile = user;
      this.editableUser = { ...user }; // Cloniamo i dati per non modificarli subito
    });
  }

  openEditForm() {
    console.log('Apertura Modale');
    this.isEditing = true;
  }

  closeEditForm() {
    console.log('Chiusura Modale');
    this.isEditing = false;
  }

  saveProfile() {
    if (this.userProfile) {
      this.userProfile = { ...this.editableUser };
      this.isEditing = false;
    }
  }
}
