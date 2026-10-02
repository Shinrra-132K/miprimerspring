import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Pareja, ParejaService } from '../../services/parejaservice';

@Component({
  selector: 'app-parejacomponent',
  standalone: false,
  templateUrl: './parejacomponent.html',
  styleUrl: './parejacomponent.css',
})
export class Parejacomponent implements OnInit {
  private parejaService = inject(ParejaService);
  private cdr = inject(ChangeDetectorRef);

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
    this.parejaService.mostrarTodo().subscribe({
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
    this.parejaService.crear(v.nombre.trim(), v.edad, !!v.existe).subscribe({
      next: (msg) => {
        f.resetForm({ existe: true });
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }

  eliminar(id: number) {
    this.parejaService.eliminarPorId(id).subscribe({
      next: () => {
        this.avisar('Pareja eliminada con exito');
        this.cargar();
      },
      error: (err) =>
        this.avisar(err.status === 404 ? 'No existe una pareja con ese id' : this.textoError(err), true),
    });
  }
}
