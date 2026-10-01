import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { NgForm } from '@angular/forms';

type ExPareja = { id: number; nombre: string; edad: number; motivoSeparacion: string };

@Component({
  selector: 'app-exparejacomponent',
  standalone: false,
  templateUrl: './exparejacomponent.html',
  styleUrl: './exparejacomponent.css',
})
export class Exparejacomponent implements OnInit {
  private http = inject(HttpClient);
  private cdr = inject(ChangeDetectorRef);
  private url = 'http://localhost:8080/exPareja';

  exparejas: ExPareja[] = [];
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
    this.http.get<ExPareja[]>(`${this.url}/mostrarTodo`).subscribe({
      next: (lista) => {
        this.exparejas = lista ?? [];
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.exparejas = [];
        this.avisar(this.textoError(err), true);
      },
    });
  }

  guardar(f: NgForm) {
    const v = f.value;
    // El back recibe el motivo con el nombre "motivoSep"
    const params = new HttpParams()
      .set('nombre', v.nombre.trim())
      .set('edad', v.edad)
      .set('motivoSep', v.motivoSeparacion.trim());
    this.http.post(`${this.url}/crear`, null, { params, responseType: 'text' }).subscribe({
      next: (msg) => {
        f.resetForm();
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
        this.avisar('Expareja eliminada con exito');
        this.cargar();
      },
      error: (err) =>
        this.avisar(err.status === 406 ? 'No existe una expareja con ese id' : this.textoError(err), true),
    });
  }
}
