import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivrosEmprestimoCadastrarComponent } from './livros-emprestimo-cadastrar.component';

describe('LivrosEmprestimoCadastrarComponent', () => {
  let component: LivrosEmprestimoCadastrarComponent;
  let fixture: ComponentFixture<LivrosEmprestimoCadastrarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LivrosEmprestimoCadastrarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivrosEmprestimoCadastrarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
