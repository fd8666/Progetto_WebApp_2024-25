import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropostaScambioComponent } from './proposta-scambio.component';

describe('PropostaScambioComponent', () => {
  let component: PropostaScambioComponent;
  let fixture: ComponentFixture<PropostaScambioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropostaScambioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropostaScambioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
