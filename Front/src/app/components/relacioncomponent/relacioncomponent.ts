import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { NgForm } from '@angular/forms';

type Relacion = { id: number; nombrePersona1: string; nombrePersona2: string };

@Component({
  selector: 'app-relacioncomponent',
  standalone: false,
  templateUrl: './relacioncomponent.html',
  styleUrl: './relacioncomponent.css',
})
export class Relacioncomponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private url = 'http://localhost:8080/relacion';

  relaciones: Relacion[] = [];
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
    // Si no hay datos el back responde 204 sin cuerpo, por eso el "?? []"
    this.http.get<Relacion[]>(`${this.url}/mostrarTodo`).subscribe({
      next: (lista) => {
        this.relaciones = lista ?? [];
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.relaciones = [];
        this.avisar(this.textoError(err), true);
      },
    });
  }

  guardar(f: NgForm) {
    const v = f.value;
    // El back recibe las dos personas como "nombre1" y "nombre2"
    const params = new HttpParams()
      .set('nombre1', v.nombrePersona1.trim())
      .set('nombre2', v.nombrePersona2.trim());
    this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' }).subscribe({
      next: (msg) => {
        f.resetForm();
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }

  eliminar(id: number, motivo: string) {
    if (!motivo.trim()) {
      this.avisar('Debes indicar la razon de la separacion', true);
      return;
    }
    const params = new HttpParams().set('id', id).set('motivoSeparacion', motivo.trim());
    this.http.delete(`${this.url}/eliminarPorId`, { params, responseType: 'text' }).subscribe({
      next: (msg) => {
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }
}
