import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ExPareja, ExParejaService } from '../../services/exparejaservice';

@Component({
  selector: 'app-exparejacomponent',
  standalone: false,
  templateUrl: './exparejacomponent.html',
  styleUrl: './exparejacomponent.css',
})
export class Exparejacomponent implements OnInit {
  private exParejaService = inject(ExParejaService);
  private cdr = inject(ChangeDetectorRef);

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
    this.exParejaService.mostrarTodo().subscribe({
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
    this.exParejaService.crear(v.nombre.trim(), v.edad, v.motivoSeparacion.trim()).subscribe({
      next: (msg) => {
        f.resetForm();
        this.avisar(msg);
        this.cargar();
      },
      error: (err) => this.avisar(this.textoError(err), true),
    });
  }

  eliminar(id: number) {
    this.exParejaService.eliminarPorId(id).subscribe({
      next: () => {
        this.avisar('Expareja eliminada con exito');
        this.cargar();
      },
      error: (err) =>
        this.avisar(err.status === 406 ? 'No existe una expareja con ese id' : this.textoError(err), true),
    });
  }
}
