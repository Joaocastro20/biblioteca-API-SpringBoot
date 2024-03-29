import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivrosCadastrarComponent } from './livros-cadastrar/livros-cadastrar.component';
import { LivrosEmprestimoCadastrarComponent } from './livros-emprestimo-cadastrar/livros-emprestimo-cadastrar.component';
import { LivrosEmprestimoFiltroComponent } from './livros-emprestimo-filtro/livros-emprestimo-filtro.component';
import { LivrosHomeComponent } from './livros-home/livros-home.component';

const routes: Routes = [
  {path: '',component:LivrosHomeComponent},
  {path:'cadastrar',component:LivrosCadastrarComponent},
  {path:'editar/:id',component:LivrosCadastrarComponent},
  {path:'emprestimo/:id',component:LivrosEmprestimoCadastrarComponent},
  {path:'emprestimo-filtro',component:LivrosEmprestimoFiltroComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
