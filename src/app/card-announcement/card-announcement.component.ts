import { Component, OnInit } from '@angular/core';
import { CardDataService } from '../services/card-data.service';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from '../header/header.component';  // Importa CommonModule per ngIf e altre direttive

@Component({
  selector: 'app-card-announcement',
  standalone: true,  // Dichiarazione come standalone
  imports: [CommonModule, HeaderComponent],  // Importa il modulo comune per supportare ngIf, etc.
  templateUrl: './card-announcement.component.html',
  styleUrls: ['./card-announcement.component.css'],
})
export class CardAnnouncementComponent implements OnInit {
  card: any;
  game: any;
  tags: any[] = [];
  tagNames: string = '';

  constructor(private cardDataService: CardDataService) {}

  ngOnInit(): void {
    // Simulazione di un card con id 1
    const cardId =1;
    this.card = this.cardDataService.getCardById(cardId);
    if (this.card) {
      this.game = this.card.game;
      this.tags = this.card.tags;
      this.tagNames = this.tags.map((tag: any) => tag.name).join(', ');
    }
  }
}
