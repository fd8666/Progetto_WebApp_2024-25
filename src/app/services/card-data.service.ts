import { Injectable } from '@angular/core';

interface Card {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  game: Game;
  tags: Tag[];
  expansion: string;  // Aggiunta dell'attributo expansion
}

interface Game {
  id: number;
  name: string;
}

interface Tag {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class CardDataService {
  private games: Game[] = [
    { id: 1, name: 'Gioco 1' },
    { id: 2, name: 'Gioco 2' },
  ];

  private tags: Tag[] = [
    { id: 1, name: 'Tag 1' },
    { id: 2, name: 'Tag 2' },
  ];

  private cards: Card[] = [
    {
      id: 1,
      name: 'Carta 1',
      description: 'La Carta 1 è un potente artefatto che ha il potere di alterare il corso della partita. Originaria dell\'espansione "Magia Antica", questa carta è stata un\'icona fin dai suoi primi giorni. Con il suo effetto unico, permette al giocatore di manipolare le risorse a disposizione, creando vantaggi strategici che possono ribaltare le sorti di un gioco inaspettatamente.',
      price: 10,
      imageUrl: '4ea7cccadf6e0686c1cfd779a8cd9bee.jpg',
      game: this.games[0],
      tags: [this.tags[0], this.tags[1]],
      expansion: 'Espansione 1',  // Aggiunta dell'espansione
    },
    {
      id: 2,
      name: 'Carta 2',
      description: 'Descrizione della carta 2',
      price: 15,
      imageUrl: '68f4c117a0efa82f1d55194807b495a9.jpg',
      game: this.games[1],
      tags: [this.tags[1]],
      expansion: 'Espansione 2',  // Aggiunta dell'espansione
    },
  ];

  getCards(): Card[] {
    return this.cards;
  }

  getCardById(id: number): Card | undefined {
    return this.cards.find((card) => card.id === id);
  }

  getGames(): Game[] {
    return this.games;
  }

  getTags(): Tag[] {
    return this.tags;
  }
}

