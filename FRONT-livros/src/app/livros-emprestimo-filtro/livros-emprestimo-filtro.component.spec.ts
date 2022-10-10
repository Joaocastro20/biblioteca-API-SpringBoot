import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivrosEmprestimoFiltroComponent } from './livros-emprestimo-filtro.component';

describe('LivrosEmprestimoFiltroComponent', () => {
  let component: LivrosEmprestimoFiltroComponent;
  let fixture: ComponentFixture<LivrosEmprestimoFiltroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LivrosEmprestimoFiltroComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivrosEmprestimoFiltroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
