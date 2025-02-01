import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoCartaComponent } from './info-carta.component';

describe('InfoCartaComponent', () => {
  let component: InfoCartaComponent;
  let fixture: ComponentFixture<InfoCartaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoCartaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoCartaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
