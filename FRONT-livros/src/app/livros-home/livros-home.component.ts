import { Component, OnInit } from '@angular/core';
import { LivrosServiceService } from '../shared/livros-service.service';
import { catchError, empty, Observable, Subject } from 'rxjs';
import { Book } from '../shared/models/book';

@Component({
  selector: 'app-livros-home',
  templateUrl: './livros-home.component.html',
  styleUrls: ['./livros-home.component.scss']
})
export class LivrosHomeComponent implements OnInit {

  listBooks!: any[];

  constructor(private service: LivrosServiceService) { }

  ngOnInit(): void {
    this.service.listBooks().subscribe(
      books => {
        this.listBooks = books;
      }
    );
  }
}
