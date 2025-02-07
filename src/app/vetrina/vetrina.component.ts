import { Component } from '@angular/core';
import {CurrencyPipe, NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {HeaderComponent} from '../header/header.component';

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
export class VetrinaComponent {
  annunci: Annuncio[] = [
    { id: 1, nome: "Mewtwo", prezzo: 8.99, immagine: "https://www.pokepedia.fr/images/thumb/b/b8/Carte_Pok%C3%A9mon_GO_086.png/640px-Carte_Pok%C3%A9mon_GO_086.png" },
    { id: 2, nome: "Usopp", prezzo: 3.99, immagine: "https://th.bing.com/th/id/OIP.5GGWqx-MimpOIIVlDhmJNQHaKV?rs=1&pid=ImgDetMain" },
    { id: 3, nome: "Jack", prezzo: 1.99, immagine: "https://th.bing.com/th/id/OIP.NQwbStd6_bms3TwRW8Y5AwHaKW?rs=1&pid=ImgDetMain" },
    { id: 4, nome: "Sakazuki", prezzo: 42.99, immagine: "https://product-images.tcgplayer.com/527011.jpg" },
    { id: 5, nome: "Dragonite", prezzo: 19.99, immagine: "https://th.bing.com/th/id/OIP.ixs55-9hMulqSLkj-rCwOgHaKR?w=570&h=791&rs=1&pid=ImgDetMain" },
    { id: 6, nome: "Raichu", prezzo: 11.99, immagine: "https://i.pinimg.com/originals/c2/92/23/c292231ea46fd89310f7f3b1cc918498.png" },
    { id: 7, nome: "Mewtwo", prezzo: 8.99, immagine: "https://www.pokepedia.fr/images/thumb/b/b8/Carte_Pok%C3%A9mon_GO_086.png/640px-Carte_Pok%C3%A9mon_GO_086.png" },
    { id: 8, nome: "Annuncio 8", prezzo: 14.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+8" },
    { id: 9, nome: "Annuncio 9", prezzo: 6.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+9" },
    { id: 10, nome: "Annuncio 10", prezzo: 13.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+10" },
    { id: 11, nome: "Annuncio 11", prezzo: 7.49, immagine: "https://via.placeholder.com/200x150?text=Annuncio+11" },
    { id: 12, nome: "Annuncio 12", prezzo: 10.49, immagine: "https://via.placeholder.com/200x150?text=Annuncio+12" },
    { id: 13, nome: "Annuncio 13", prezzo: 10.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+1" },
    { id: 14, nome: "Annuncio 14", prezzo: 5.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+2" },
    { id: 15, nome: "Annuncio 15", prezzo: 7.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+3" },
    { id: 16, nome: "Annuncio 16", prezzo: 12.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+4" },
    { id: 17, nome: "Annuncio 17", prezzo: 9.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+5" },
    { id: 18, nome: "Annuncio 18", prezzo: 11.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+6" },
    { id: 19, nome: "Annuncio 19", prezzo: 8.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+7" },
    { id: 20, nome: "Annuncio 20", prezzo: 14.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+8" },
    { id: 21, nome: "Annuncio 21", prezzo: 6.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+9" },
    { id: 22, nome: "Annuncio 22", prezzo: 13.99, immagine: "https://via.placeholder.com/200x150?text=Annuncio+10" },
    { id: 23, nome: "Annuncio 23", prezzo: 7.49, immagine: "https://via.placeholder.com/200x150?text=Annuncio+11" },
    { id: 24, nome: "Annuncio 24", prezzo: 10.49, immagine: "https://via.placeholder.com/200x150?text=Annuncio+12" },
  ];

  paginaAttuale = 1;
  annunciPerPage = 12;

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
