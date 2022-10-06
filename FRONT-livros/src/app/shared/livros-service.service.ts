import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import {HttpClient} from '@angular/common/http'
import { Book } from './models/book';

@Injectable({
  providedIn: 'root'
})
export class LivrosServiceService {

  private readonly API = `${environment.API}api/books`;

  constructor(private http:HttpClient) { }

  listBooks(){
    return this.http.get<Book[]>(this.API).subscribe(dados=> console.log(dados));
  }

}
