import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import {HttpClient} from '@angular/common/http'
import { Book } from './models/book';
import {map} from 'rxjs/operators'
import { ResolveData } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LivrosServiceService {

  private readonly API = `${environment.API}`;

  constructor(private http:HttpClient) { }

  listBooks(){
    return this.http.get<ResolveData>(this.API).pipe(
      map(
        resp => {
          return resp['content']
        }
      )
    );
  }

  salvarBook(book:Book){
    return this.http.post(this.API,book);
  }
}
