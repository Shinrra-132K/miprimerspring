import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface Relacion {
  id: number;
  nombrePersona1: string;
  nombrePersona2: string;
}

@Injectable({ providedIn: 'root' })
export class RelacionService {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/relacion';

  mostrarTodo(): Observable<Relacion[] | null> {
    return this.http.get<Relacion[]>(`${this.url}/mostrarTodo`);
  }

  crear(nombre1: string, nombre2: string): Observable<string> {
    const params = new HttpParams().set('nombre1', nombre1).set('nombre2', nombre2);
    return this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' });
  }

  eliminarPorId(id: number, motivoSeparacion: string): Observable<string> {
    const params = new HttpParams().set('id', id).set('motivoSeparacion', motivoSeparacion);
    return this.http.delete(`${this.url}/eliminarPorId`, { params, responseType: 'text' });
  }
}
