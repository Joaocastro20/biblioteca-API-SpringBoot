import { Component, OnInit } from '@angular/core';
import { LivrosServiceService } from '../shared/livros-service.service';
import { Emprestimo } from '../shared/models/emprestimo';

@Component({
  selector: 'app-livros-emprestimo-filtro',
  templateUrl: './livros-emprestimo-filtro.component.html',
  styleUrls: ['./livros-emprestimo-filtro.component.scss']
})
export class LivrosEmprestimoFiltroComponent implements OnInit {

  emprestimos!: Emprestimo[];

  constructor(
    private service:LivrosServiceService
  ) { }

  ngOnInit(): void {
  }

  onSearch(search:any,option:any){
    console.log(search,option)
    if(option==1){
      let search_pesquisa = 'customer='+search;
      return this.service.filtrarLoan(search_pesquisa).subscribe(
        emprestimo => {
          this.emprestimos = emprestimo;
        }
      );
    }else if(option==2){
      let search_pesquisa = 'isbn='+search;
      return this.service.filtrarLoan(search_pesquisa).subscribe(
        emprestimo => {
          this.emprestimos = emprestimo;
        }
      );
    }
    return null;
  }

}
