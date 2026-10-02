import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface ExPareja {
  id: number;
  nombre: string;
  edad: number;
  motivoSeparacion: string;
}

@Injectable({ providedIn: 'root' })
export class ExParejaService {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/exPareja';

  mostrarTodo(): Observable<ExPareja[] | null> {
    return this.http.get<ExPareja[]>(`${this.url}/mostrarTodo`);
  }

  crear(nombre: string, edad: number, motivoSeparacion: string): Observable<string> {
    const params = new HttpParams().set('nombre', nombre).set('edad', edad).set('motivoSep', motivoSeparacion);
    return this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' });
  }

  eliminarPorId(id: number): Observable<string> {
    const params = new HttpParams().set('id', id);
    return this.http.delete(`${this.url}/eliminarPorId`, { params, responseType: 'text' });
  }
}
