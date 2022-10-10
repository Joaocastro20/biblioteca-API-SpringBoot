import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LivrosHomeComponent } from './livros-home/livros-home.component';
import { LivrosCadastrarComponent } from './livros-cadastrar/livros-cadastrar.component';
import { LivrosEmprestimoCadastrarComponent } from './livros-emprestimo-cadastrar/livros-emprestimo-cadastrar.component';
import { LivrosEmprestimoFiltroComponent } from './livros-emprestimo-filtro/livros-emprestimo-filtro.component';

@NgModule({
  declarations: [
    AppComponent,
    LivrosHomeComponent,
    LivrosCadastrarComponent,
    LivrosEmprestimoCadastrarComponent,
    LivrosEmprestimoFiltroComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
