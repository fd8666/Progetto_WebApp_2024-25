import { Component, OnInit } from '@angular/core';
import { UserProfileService, UserProfile } from '../services/user-profile.service';
import { AuthService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

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

  // Simulazione di utenti disponibili
  private mockUsers: UserProfile[] = [
    {
      id:1,
      username: 'MarioRossi',
      email: 'test@email.com',
      bio: 'Collezionista di carte rare.',
      profileImage: 'img_2.png'
    },
    {
      id:2,
      username: 'LuigiVerdi',
      email: 'test@gmail.com',
      bio: 'Amante delle carte vintage.',
      profileImage: 'img_2.png'
    },
    {
      id:3,
      username: 'AnnaBianchi',
      email: 'anna.bianchi@example.com',
      bio: '',
      profileImage: 'img_2.png'
    }
  ];

  constructor(private authService: AuthService, private userProfileService: UserProfileService) {}

  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile() {
    const userEmail = this.authService.email;
    const user = this.mockUsers.find(u => u.email === userEmail);

    if (user) {
      this.userProfile = user;
      this.editableUser = { ...user };
      this.errorMessage = null;
    } else {
      this.errorMessage = 'Utente non trovato.';
    }
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
