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

import co.edu.unbosque.miprimerback.entity.Pareja;
import co.edu.unbosque.miprimerback.service.ParejaService;

@RestController
@RequestMapping(path = "/pareja")
@CrossOrigin(origins = {"*"})
public class ParejaController {
	@Autowired
	private ParejaService parejaServ;
	
	public ParejaController() {
		// TODO Auto-generated constructor stub
	}
	@PostMapping("/crear")
	public ResponseEntity<String> crearDato(@RequestParam String nombre, @RequestParam int edad, @RequestParam boolean existe) {
		int status = parejaServ.crear(new Pareja(nombre, edad, existe));
		if (status == 0) {
			return new ResponseEntity<>("Dato creado con exito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el dato", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	@GetMapping("/mostrarTodo")
	public ResponseEntity<List<Pareja>> mostrarTodo() {
		List<Pareja> listaParejas = parejaServ.mostrarTodo();
		if (listaParejas.isEmpty()) {
			return new ResponseEntity<List<Pareja>>(listaParejas, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Pareja>>(listaParejas, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping("/eliminarPorId")
	public ResponseEntity<Long> eliminarPorId(@RequestParam long id) {
		int status = parejaServ.eliminarPorId(id);
		if (status == 1) {
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Long>(HttpStatus.OK);
		}
	}
	
	@PutMapping("/actualizarPorId")
	public ResponseEntity<String> actualizarPorId(@RequestParam long id, @RequestParam String nombre, @RequestParam int edad, @RequestParam boolean existe) {
		int status = parejaServ.actualiarPorId(id, new Pareja(nombre, edad, existe));
		if (status == 0) {
			return new ResponseEntity<>("Dato actualizado con exito", HttpStatus.ACCEPTED);
		} else if (status == 1) {
			return new ResponseEntity<>("El id no existe, se creo un nuevo dato", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al actualizar el dato", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@DeleteMapping("/borrarPorNombre")
	public ResponseEntity<String> eliminarPorNombre(String nombre) {
		int status = parejaServ.eliminarPorNombre(nombre);
		if (status == 0) {
			return new ResponseEntity<>("Dato eliminad con exito", HttpStatus.OK);
		} else if(status == 1) {
			return new ResponseEntity<>("No existe una pareja con ese nombre", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>("Hay varias parejas con ese nombre, usa eliminar por id", HttpStatus.CONFLICT);
		}
	}
	
	

}
