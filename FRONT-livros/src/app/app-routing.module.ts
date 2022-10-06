import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivrosHomeComponent } from './livros-home/livros-home.component';

const routes: Routes = [
  {path: '',component:LivrosHomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
