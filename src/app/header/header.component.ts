import {Component, OnInit, Renderer2} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
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
