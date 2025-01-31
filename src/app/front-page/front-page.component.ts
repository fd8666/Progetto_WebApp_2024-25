import { Component, OnInit, Renderer2 } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css'],
  imports: [
    RouterLink
  ],
  standalone: true
})
export class FrontPageComponent implements OnInit {

  constructor(private renderer: Renderer2) {}

  ngOnInit() {
    const accountButton = this.renderer.selectRootElement('#accountButton', true);
    const accountMenu = accountButton.nextElementSibling;

    this.renderer.listen(accountButton, 'click', () => {
      accountMenu.classList.toggle('show');
    });

    const cartButton = this.renderer.selectRootElement('#cartButton', true);
    const cartMenu = cartButton.nextElementSibling;

    this.renderer.listen(cartButton, 'click', () => {
      cartMenu.classList.toggle('show');
    });

    this.renderer.listen(document, 'click', (event: Event) => {
      if (!(event.target as HTMLElement).matches('#accountButton') &&
        !(event.target as HTMLElement).matches('#cartButton')) {
        document.querySelectorAll('.dropdown-menu').forEach(menu => {
          if (menu.classList.contains('show')) {
            menu.classList.remove('show');
          }
        });
      }
    });
  }
}
