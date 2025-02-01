import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-contact-map',
  templateUrl: './contact-map.component.html',
  standalone: true,
  styleUrls: ['./contact-map.component.css']
})
export class ContactMapComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    // Inizializzare la mappa
    const map = L.map('map').setView([39.3617822814888, 16.226076051838028], 16);  // Imposta la latitudine e longitudine

    // Aggiungi il layer OpenStreetMap alla mappa
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    L.marker([39.3617822814888, 16.226076051838028]).addTo(map)
      .bindPopup('<div style="text-align: center;">Siamo qui!<br>Università della Calabria<br>Cubo 31</div>')
      .openPopup()
      .setIcon(L.icon({
        iconUrl: 'pin.png',
        iconSize: [32, 32],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32]
      }));

  }
}
