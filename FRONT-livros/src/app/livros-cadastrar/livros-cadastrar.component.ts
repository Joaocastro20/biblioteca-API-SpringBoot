import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, createUrlTreeFromSnapshot } from '@angular/router';
import { LivrosServiceService } from '../shared/livros-service.service';
import { map, switchMap } from 'rxjs/operators'

@Component({
  selector: 'app-livros-cadastrar',
  templateUrl: './livros-cadastrar.component.html',
  styleUrls: ['./livros-cadastrar.component.scss']
})
export class LivrosCadastrarComponent implements OnInit {

  formBook!: FormGroup;

  constructor(
    private formBuider: FormBuilder,
    private service: LivrosServiceService,
    private route: ActivatedRoute

  ) { }

  ngOnInit(): void {


    this.route.params.pipe(
      map((params: any) => params['id']),
      switchMap(id => this.service.buscarBookId(id))
    )
      .subscribe(curso => this.updateForm(curso));

    this.formBook = this.formBuider.group({
      id: [null],
      title: [null],
      author: [null],
      isbn: [null]
    })
  }

  updateForm(book: any) {
    this.formBook.patchValue({
      id: book.id,
      title: book.title,
      author: book.author
    })
  }

  onSubmit() {
    if (this.formBook.value.id) {
      this.service.atualizarBook(this.formBook.value).subscribe(
        sucess => alert('update, successo'),
        error => alert('update, Error')
      )
    } else {
      this.service.salvarBook(this.formBook.value).subscribe(
        sucess => alert('successo'),
        error => alert('Error')
      );
      this.limparFormBook();
    }

  }

  limparFormBook() {
    this.formBook.reset();
  }

}
