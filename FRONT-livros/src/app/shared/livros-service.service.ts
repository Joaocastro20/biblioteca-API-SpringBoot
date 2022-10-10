import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import {HttpClient} from '@angular/common/http'
import { Book } from './models/book';
import {map,take} from 'rxjs/operators'
import { ResolveData } from '@angular/router';
import { Emprestimo } from './models/emprestimo';
import { FormGroup } from '@angular/forms';
import { ResponseData } from './models/responseData';

@Injectable({
  providedIn: 'root'
})
export class LivrosServiceService {

  private readonly API = `${environment.API}`;

  constructor(private http:HttpClient) { }

  listBooks(){
    return this.http.get<ResolveData>(`${this.API}/api/books/`).pipe(
      map(
        resp => {
          return resp['content']
        }
      )
    );
  }

  salvarBook(book:Book){
    return this.http.post(`${this.API}/api/books/`,book);
  }

  atualizarBook(book:Book){
    return this.http.put(`${this.API}/api/books/${book.id}`,book).pipe(take(1));
  }

  deletarBook(book:Book){
    return this.http.delete(`${this.API}/api/books/${book.id}`).pipe(take(1));
  }

  buscarBookId(id:any){
    return this.http.get(`${this.API}/api/books/${id}`).pipe(take(1));
  }

  salvarLoan(emprestimo:FormGroup){ 
    return this.http.post(`${this.API}/api/loans/`,emprestimo.value)
  }

  filtrarLoan(search:any){
    return this.http.get<ResolveData>(`${this.API}/api/loans/?${search}`).pipe(
      map(
        resp => {
          return resp['content'];
          console.log(resp);
        }
      ))
  }
}
