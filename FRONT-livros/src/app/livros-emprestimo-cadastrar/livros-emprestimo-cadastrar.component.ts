import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs/operators'
import { LivrosServiceService } from '../shared/livros-service.service';
import { Emprestimo } from '../shared/models/emprestimo';

@Component({
  selector: 'app-livros-emprestimo-cadastrar',
  templateUrl: './livros-emprestimo-cadastrar.component.html',
  styleUrls: ['./livros-emprestimo-cadastrar.component.scss']
})
export class LivrosEmprestimoCadastrarComponent implements OnInit {

  formEmprestimo!: FormGroup;

  formBook!: FormGroup;

  emprestimo!: Emprestimo;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private service: LivrosServiceService
  ) { }

  ngOnInit(): void {

    this.route.params.pipe(
      map((params: any) => params['id']),
      switchMap(id => this.service.buscarBookId(id))
    )
      .subscribe(book => this.updateForm(book));

    this.formEmprestimo = this.formBuilder.group({
      id: [null],
      isbn: [null],
      customer: [null,[Validators.required,Validators.minLength(3), Validators.maxLength(20)]],
      email: [null,[Validators.required,Validators.email]],
      book: this.formBuilder.group({
        id: [null],
        title: [null],
        author: [null],
        isbn: [null]
      })
    })

    this.formBook = this.formBuilder.group({
      id: [null],
      title: [null],
      author: [null],
      isbn: [null]
    })
  }

  onSubmit() {
    this.service.salvarLoan(this.formEmprestimo).subscribe(
      sucess => alert('successo'),
      error => alert('Error, Livro ja emprestado')
    );
  }

  updateFormEmprestimo(emprestimo: any) {
    this.formEmprestimo.patchValue({
      id: emprestimo.id,
      isbn: emprestimo.isbn,
      customer: emprestimo.customer,
      email: emprestimo.email,
      book: this.formEmprestimo.patchValue({
        id: emprestimo.book.id,
        title: emprestimo.book.title,
        author: emprestimo.book.author,
        isbn: emprestimo.book.isbn
      })
    })
  }

  updateForm(book: any) {
    this.formBook.patchValue({
      id: book.id,
      title: book.title,
      author: book.author,
      isbn: book.isbn
    })
    this.setarIsbn();
  }

  setarIsbn(){
    this.formEmprestimo.patchValue({
      isbn: this.formBook.value.isbn
    })
  }

  validarFormEmprestimo(){
    return this.formEmprestimo.valid;
  }

}
