import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { NgForm } from '@angular/forms';

type Pareja = { id: number; nombre: string; edad: number; existe: boolean };

@Component({
  selector: 'app-parejacomponent',
  standalone: false,
  templateUrl: './parejacomponent.html',
  styleUrl: './parejacomponent.css',
})
export class Parejacomponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private url = 'http://localhost:8080/pareja';

  parejas: Pareja[] = [];
  mensaje = '';
  esError = false;

  ngOnInit() {
    this.cargar();
  }

  private avisar(texto: string, error = false) {
    this.mensaje = texto;
    this.esError = error;
    this.cdr.markForCheck();
  }

  private textoError(err: HttpErrorResponse): string {
    if (err.status === 0) return 'No se pudo conectar con el servidor';
    return typeof err.error === 'string' && err.error ? err.error : `Error ${err.status}`;
  }

  cargar() {
    this.http.get<Pareja[]>(`${this.url}/mostrarTodo`).subscribe({
      next: (lista) => {
        this.parejas = lista ?? [];
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.parejas = [];
        this.avisar(this.textoError(err), true);
      },
    });
  }

  guardar(f: NgForm) {
    const v = f.value;
    const params = new HttpParams()
      .set('nombre', v.nombre.trim())
      .set('edad', v.edad)
      .set('existe', !!v.existe);
    this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' }).subscribe({
      next: (msg) => {
        f.resetForm({ existe: true });
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }

  eliminar(id: number) {
    const params = new HttpParams().set('id', id);
    this.http.delete(`${this.url}/eliminarPorId`, { params, responseType: 'text' }).subscribe({
      next: () => {
        this.avisar('Pareja eliminada con exito');
        this.cargar();
      },
      error: (err) =>
        this.avisar(err.status === 404 ? 'No existe una pareja con ese id' : this.textoError(err), true),
    });
  }
}
