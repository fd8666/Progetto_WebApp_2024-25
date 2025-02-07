import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CurrencyPipe, NgForOf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

interface Annuncio {
  id: number;
  nome: string;
  prezzo: number;
  immagine: string;
}

@Component({
  selector: 'app-vetrina',
  templateUrl: './vetrina.component.html',
  standalone: true,
  imports: [CurrencyPipe, NgForOf, RouterLink, HeaderComponent],
  styleUrls: ['./vetrina.component.scss']
})
export class VetrinaComponent implements OnInit {
  annunci: Annuncio[] = [];
  paginaAttuale = 1;
  annunciPerPage = 12;
  apiUrl = 'http://localhost:8080/api/cards/getCards'; // Assicurati che il backend sia avviato

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.caricaAnnunci();
  }

  caricaAnnunci() {
    this.http.get<{ cards: any[] }>(this.apiUrl).subscribe({
      next: (response) => {
        if (response?.cards && Array.isArray(response.cards)) {
          this.annunci = response.cards.map(card => ({
            id: card.id,
            nome: card.name || 'Nome non disponibile',  // Cambio di nome da nome a name
            prezzo: card.prezzo || 0,                   // Uso di prezzo
            immagine: card.img || 'https://via.placeholder.com/600x840?text=No+Image'  // Uso dell'immagine
          }));
        }
      },
      error: (error) => console.error('Errore nel recupero degli annunci:', error)
    });
  }

  get maxPagine(): number {
    return Math.ceil(this.annunci.length / this.annunciPerPage);
  }

  get annunciPaginati(): Annuncio[] {
    const inizio = (this.paginaAttuale - 1) * this.annunciPerPage;
    return this.annunci.slice(inizio, inizio + this.annunciPerPage);
  }

  precedente(): void {
    if (this.paginaAttuale > 1) {
      this.paginaAttuale--;
    }
  }

  successivo(): void {
    if (this.paginaAttuale < this.maxPagine) {
      this.paginaAttuale++;
    }
  }
}
