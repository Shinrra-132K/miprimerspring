package co.edu.unbosque.miprimerback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerback.entity.ExPareja;
import co.edu.unbosque.miprimerback.entity.Pareja;
import co.edu.unbosque.miprimerback.entity.Relacion;
import co.edu.unbosque.miprimerback.repository.ExParejaRepository;
import co.edu.unbosque.miprimerback.repository.ParejaRepository;
import co.edu.unbosque.miprimerback.repository.RelacionRepository;

@Service
public class RelacionService {
	@Autowired
	private RelacionRepository relacionRep;
	@Autowired
	private ParejaRepository parejaRepo;
	@Autowired
	private ExParejaRepository exParejaRepo;

	public RelacionService() {
		// TODO Auto-generated constructor stub
	}

	public long contar() {
		return relacionRep.count();
	}

	public boolean existe(long id) {
		return relacionRep.existsById(id);
	}

	private boolean estaRegistrada(String nombre) {
		return !parejaRepo.findByNombre(nombre).isEmpty() || !exParejaRepo.findByNombre(nombre).isEmpty();
	}

	public void pasarExParejaAPareja(String nombre) {
		List<ExPareja> listaExParejas = exParejaRepo.findByNombre(nombre);
		if (listaExParejas.isEmpty()) {
			return;
		}
		ExPareja ex = listaExParejas.get(0);
		if (parejaRepo.findByNombre(nombre).isEmpty()) {
			parejaRepo.save(new Pareja(ex.getNombre(), ex.getEdad(), true));
		}
		exParejaRepo.delete(ex);
	}

	public void pasarParejaAExPareja(String nombre, String motivoSeparacion) {
		if (relacionRep.existsByNombrePersona1OrNombrePersona2(nombre, nombre)) {
			return;
		}
		List<Pareja> listaParejas = parejaRepo.findByNombre(nombre);
		if (listaParejas.isEmpty()) {
			return;
		}
		Pareja p = listaParejas.get(0);
		if (exParejaRepo.findByNombre(nombre).isEmpty()) {
			exParejaRepo.save(new ExPareja(p.getNombre(), p.getEdad(), motivoSeparacion));
		}
		parejaRepo.delete(p);
	}

	public int crear(Relacion nuevoDato) {
		if (nuevoDato == null || nuevoDato.getNombrePersona1() == null || nuevoDato.getNombrePersona2() == null) {
			return 2;
		}
		String nombre1 = nuevoDato.getNombrePersona1().trim();
		String nombre2 = nuevoDato.getNombrePersona2().trim();
		if (nombre1.isEmpty() || nombre2.isEmpty()) {
			return 2;
		}

		nuevoDato.setNombrePersona1(nombre1);
		nuevoDato.setNombrePersona2(nombre2);

		if (!estaRegistrada(nombre1) || !estaRegistrada(nombre2)) {
			return 3;
		}

		try {
			pasarExParejaAPareja(nombre1);
			pasarExParejaAPareja(nombre2);
			relacionRep.save(nuevoDato);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public List<Relacion> mostrarTodo() {
		return (List<Relacion>) relacionRep.findAll();
	}

	public int eliminarPorId(long id, String motivoSeparacion) {
		if (motivoSeparacion == null || motivoSeparacion.isBlank()) {
			return 3;
		}
		motivoSeparacion = motivoSeparacion.trim();
		Optional<Relacion> encontrado = relacionRep.findById(id);
		if (encontrado.isEmpty()) {
			return 1;
		}
		Relacion relacion = encontrado.get();
		relacionRep.delete(relacion);
		pasarParejaAExPareja(relacion.getNombrePersona1(), motivoSeparacion);
		pasarParejaAExPareja(relacion.getNombrePersona2(), motivoSeparacion);
		return 0;
	}

	public int eliminarPorNombre(String nombre1, String nombre2, String motivoSeparacion) {
		if (motivoSeparacion == null || motivoSeparacion.isBlank()) {
			return 3;
		}
		motivoSeparacion = motivoSeparacion.trim();
		List<Relacion> encontrados = relacionRep.findByNombrePersona1AndNombrePersona2(nombre1, nombre2);
		if (encontrados.isEmpty()) {
			return 1;
		}
		if (encontrados.size() > 1) {
			return 2;
		}
		Relacion relacion = encontrados.get(0);
		relacionRep.delete(relacion);
		pasarParejaAExPareja(relacion.getNombrePersona1(), motivoSeparacion);
		pasarParejaAExPareja(relacion.getNombrePersona2(), motivoSeparacion);
		return 0;
	}

	public int actualizarPorId(long id, Relacion nuevoDato) {
		if (nuevoDato == null) {
			return 2;
		}
		Optional<Relacion> encontrado = relacionRep.findById(id);
		if (encontrado.isPresent()) {
			Relacion temp = encontrado.get();
			temp.setNombrePersona1(nuevoDato.getNombrePersona1());
			temp.setNombrePersona2(nuevoDato.getNombrePersona2());
			relacionRep.save(temp);
			return 0;
		} else {
			return 1;
		}
	}

}