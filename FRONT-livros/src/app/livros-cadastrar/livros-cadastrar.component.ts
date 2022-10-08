import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { LivrosServiceService } from '../shared/livros-service.service';
import { Book } from '../shared/models/book';

@Component({
  selector: 'app-livros-cadastrar',
  templateUrl: './livros-cadastrar.component.html',
  styleUrls: ['./livros-cadastrar.component.scss']
})
export class LivrosCadastrarComponent implements OnInit {

  formBook!: FormGroup;

  constructor(
    private formBuider:FormBuilder,
    private service:LivrosServiceService
              
    ) { }

  ngOnInit(): void {
    this.formBook = this.formBuider.group({
      title:[null],
      author:[null],
      isbn:[null]
    })
  }

  onSubmit(){
    this.service.salvarBook(this.formBook.value).subscribe(
      sucess => alert('successo'),
      error => alert('Error')
    );
    this.limparFormBook();
  }

  limparFormBook(){
    this.formBook.reset();
  }

}
