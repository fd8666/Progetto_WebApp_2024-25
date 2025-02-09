import { Component, OnInit } from '@angular/core';
import { UserProfileService, UserProfile } from '../services/user-profile.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import {HeaderComponent} from '../header/header.component';
import {FooterComponent} from '../footer/footer.component';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css'],
  standalone: true,
  imports: [NgIf, FormsModule, HeaderComponent, FooterComponent]
})
export class ProfilePageComponent implements OnInit {
  userProfile!: UserProfile;
  editableUser!: UserProfile;
  isEditing = false;
  previewImage: string | ArrayBuffer | null = null;
  errorMessage: string | null = null;

  constructor(private userProfileService: UserProfileService) {}

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile() {
    this.userProfileService.getUserProfile('').subscribe({
      next: (user) => {
        this.userProfile = user;
        this.editableUser = { ...user };
        this.errorMessage = null;
      },
      error: (err) => {
        console.error('Errore nel caricamento del profilo:', err);
        this.errorMessage = 'Impossibile caricare il profilo.';
      }
    });
  }

  openEditForm() {
    this.isEditing = true;
  }

  closeEditForm() {
    this.isEditing = false;
    this.previewImage = null;
  }

  saveProfile() {
    this.userProfile = { ...this.editableUser };
    if (this.previewImage) {
      this.userProfile.profileImage = this.previewImage as string;
    }
    this.isEditing = false;
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.previewImage = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }
}
