import { Component, OnInit } from '@angular/core';
import { LivrosServiceService } from '../shared/livros-service.service';
import { catchError, delay, empty, Observable, Subject } from 'rxjs';
import { Book } from '../shared/models/book';
import { Router } from '@angular/router';

@Component({
  selector: 'app-livros-home',
  templateUrl: './livros-home.component.html',
  styleUrls: ['./livros-home.component.scss']
})
export class LivrosHomeComponent implements OnInit {

  listBooks!: any[];

  constructor(
    private service: LivrosServiceService,
    private router:Router
    ) { }

  ngOnInit(): void {
    this.service.listBooks().subscribe(
      books => {
        this.listBooks = books;
      }
    );
  }

  updateBook(book:Book){
    this.router.navigate(['editar',book.id]);
  }

  deletarBook(book:Book){
    this.service.deletarBook(book).subscribe();
    this.refresh();
  }

  refresh(): void {
    window.location.reload();
  }
}
