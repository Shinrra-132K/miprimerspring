import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Relacion, RelacionService } from '../../services/relacionservice';

@Component({
  selector: 'app-relacioncomponent',
  standalone: false,
  templateUrl: './relacioncomponent.html',
  styleUrl: './relacioncomponent.css',
})
export class Relacioncomponent implements OnInit {
  private relacionService = inject(RelacionService);
  private cdr = inject(ChangeDetectorRef);

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
    this.relacionService.mostrarTodo().subscribe({
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
    this.relacionService.crear(v.nombrePersona1.trim(), v.nombrePersona2.trim()).subscribe({
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
    this.relacionService.eliminarPorId(id, motivo.trim()).subscribe({
      next: (msg) => {
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }
}
