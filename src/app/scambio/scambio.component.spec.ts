import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScambioComponent } from './scambio.component';

describe('ScambioComponent', () => {
  let component: ScambioComponent;
  let fixture: ComponentFixture<ScambioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScambioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScambioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
