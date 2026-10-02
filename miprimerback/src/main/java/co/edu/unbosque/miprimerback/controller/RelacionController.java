package co.edu.unbosque.miprimerback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.miprimerback.entity.Relacion;
import co.edu.unbosque.miprimerback.service.RelacionService;

@RestController
@RequestMapping(path = "/relacion")
@CrossOrigin(origins = { "*" })
public class RelacionController {

	@Autowired
	private RelacionService relacionServ;

	public RelacionController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crearDato(@RequestParam String nombre1, @RequestParam String nombre2) {
		int status = relacionServ.crear(new Relacion(nombre1, nombre2));
		if (status == 0) {
			return new ResponseEntity<>("Relacion creada con exito", HttpStatus.CREATED);
		} else if (status == 3) {
			return new ResponseEntity<>("Las dos personas deben estar registradas en pareja o expareja",
					HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Error al crear la relacion", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/mostrarTodo")
	public ResponseEntity<List<Relacion>> mostrarTodo() {
		List<Relacion> listaRelaciones = relacionServ.mostrarTodo();
		if (listaRelaciones.isEmpty()) {
			return new ResponseEntity<List<Relacion>>(listaRelaciones, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Relacion>>(listaRelaciones, HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping("/eliminarPorId")
	public ResponseEntity<String> eliminarPorId(@RequestParam long id, @RequestParam String motivoSeparacion) {
		int status = relacionServ.eliminarPorId(id, motivoSeparacion);
		if (status == 0) {
			return new ResponseEntity<>("Relacion eliminada con exito", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<>("No existe ninguna relacion con ese id", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Debes indicar la razon de la separacion", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/eliminarPorNombres")
	public ResponseEntity<String> eliminarPorNombres(@RequestParam String nombre1, @RequestParam String nombre2,
			@RequestParam String motivoSeparacion) {
		int status = relacionServ.eliminarPorNombre(nombre1, nombre2, motivoSeparacion);
		if (status == 0) {
			return new ResponseEntity<>("Relacion eliminada con exito", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<>("No existe ninguna relacion con esos nombres", HttpStatus.NOT_FOUND);
		} else if (status == 2) {
			return new ResponseEntity<>("Hay varias relaciones con esos nombres, use eliminar por id",
					HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>("Debes indicar la razon de la separacion", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping("/actualizarPorId")
	public ResponseEntity<String> actualizarPorId(@RequestParam long id, @RequestParam String nombre1,
			@RequestParam String nombre2) {
		int status = relacionServ.actualizarPorId(id, new Relacion(nombre1, nombre2));
		if (status == 0) {
			return new ResponseEntity<>("Relacion actualizada con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("No existe ninguna relacion con ese id", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>("Error al actualizar la relacion", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}