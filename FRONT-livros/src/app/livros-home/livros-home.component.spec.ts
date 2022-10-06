import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivrosHomeComponent } from './livros-home.component';

describe('LivrosHomeComponent', () => {
  let component: LivrosHomeComponent;
  let fixture: ComponentFixture<LivrosHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LivrosHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivrosHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
