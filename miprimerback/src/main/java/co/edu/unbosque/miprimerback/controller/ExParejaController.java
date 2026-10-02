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

import co.edu.unbosque.miprimerback.entity.ExPareja;
import co.edu.unbosque.miprimerback.service.ExParejaService;

@RestController
@RequestMapping(path = "/exPareja")
@CrossOrigin(origins = { "*" })
public class ExParejaController {

	@Autowired
	private ExParejaService exParejaServ;

	public ExParejaController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crearDato(@RequestParam String nombre, @RequestParam int edad,
			@RequestParam String motivoSep) {
		int status = exParejaServ.crear(new ExPareja(nombre, edad, motivoSep));
		if (status == 0) {
			return new ResponseEntity<>("Dato creado con exito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el dato", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/mostrarTodo")
	public ResponseEntity<List<ExPareja>> mostrarTodo() {
		List<ExPareja> listaExParejas = exParejaServ.mostrarTodo();
		if (listaExParejas.isEmpty()) {
			return new ResponseEntity<List<ExPareja>>(listaExParejas, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ExPareja>>(listaExParejas, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping("/eliminarPorId")
	public ResponseEntity<Long> eliminarPorId(@RequestParam long id) {
		int status = exParejaServ.eliminarPorId(id);
		if (status == 1) {
			return new ResponseEntity<Long>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<Long>(HttpStatus.OK);
		}
	}
	
	@PutMapping("/actualizarPorId")
	public ResponseEntity<String> actualizarPorId(@RequestParam long id, @RequestParam String nombre, @RequestParam int edad, @RequestParam String MotivoSep) {
		int status = exParejaServ.actualizarPorId(id, new ExPareja(nombre, edad, MotivoSep));
		if (status == 0) {
			return new ResponseEntity<>("Dato actualizado exitosamente", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("El id no existe, se creo un nuevo dato", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al actualizar el dato", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@DeleteMapping("/eliminarPorNombre")
	public ResponseEntity<String> eliminarPorNombre(String nombre) {
		int status = exParejaServ.eliminarPorNombre(nombre);
		if (status == 0) {
			return new ResponseEntity<String>("Dato eliminado con exito", HttpStatus.OK);
		} else if (status == 1) {
			return new ResponseEntity<String>("No existe una ex pareja con ese nombre", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>("Hay varias ex parejas con ese nombre", HttpStatus.CONFLICT);
		}
	}
	

}
