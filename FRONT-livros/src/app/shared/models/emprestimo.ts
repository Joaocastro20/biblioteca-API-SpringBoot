import { Book } from "./book";

export interface Emprestimo{
    id: any,
    isbn: string,
    customer: string,
    email:string,
    book:Book
}