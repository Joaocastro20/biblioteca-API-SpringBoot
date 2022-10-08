import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivrosCadastrarComponent } from './livros-cadastrar/livros-cadastrar.component';
import { LivrosHomeComponent } from './livros-home/livros-home.component';

const routes: Routes = [
  {path: '',component:LivrosHomeComponent},
  {path:'cadastrar',component:LivrosCadastrarComponent},
  {path:'editar/:id',component:LivrosCadastrarComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
