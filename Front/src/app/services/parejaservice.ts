import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface Pareja {
  id: number;
  nombre: string;
  edad: number;
  existe: boolean;
}

@Injectable({ providedIn: 'root' })
export class ParejaService {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/pareja';

  mostrarTodo(): Observable<Pareja[] | null> {
    return this.http.get<Pareja[]>(`${this.url}/mostrarTodo`);
  }

  crear(nombre: string, edad: number, existe: boolean): Observable<string> {
    const params = new HttpParams().set('nombre', nombre).set('edad', edad).set('existe', existe);
    return this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' });
  }

  eliminarPorId(id: number): Observable<string> {
    const params = new HttpParams().set('id', id);
    return this.http.delete(`${this.url}/eliminarPorId`, { params, responseType: 'text' });
  }
}
