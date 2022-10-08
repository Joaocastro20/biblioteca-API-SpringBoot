import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivrosCadastrarComponent } from './livros-cadastrar.component';

describe('LivrosCadastrarComponent', () => {
  let component: LivrosCadastrarComponent;
  let fixture: ComponentFixture<LivrosCadastrarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LivrosCadastrarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivrosCadastrarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
