package co.edu.unbosque.miprimerback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerback.entity.Pareja;
import co.edu.unbosque.miprimerback.repository.ParejaRepository;

@Service
public class ParejaService {
	
	@Autowired
	private ParejaRepository parejaRepo;
	
	public ParejaService() {
		// TODO Auto-generated constructor stub
	}
	public long contar() {
		return parejaRepo.count();
	}
	
	public boolean existe(long id) {
		return parejaRepo.existsById(id);
	}
	
	public int crear(Pareja nuevoDato) {
		try {
			parejaRepo.save(nuevoDato);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}
	
	public List<Pareja> mostrarTodo() {
		return (List<Pareja>) parejaRepo.findAll();
	}
	
	public int eliminarPorId(long id) {
		Optional<Pareja> encontrado = parejaRepo.findById(id);
		if (encontrado.isPresent()) {
			parejaRepo.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}
	
	public int actualiarPorId(long id, Pareja nuevoDato) {
		Optional<Pareja> encontrado = parejaRepo.findById(id);
		if (encontrado.isPresent() && nuevoDato != null) {
			Pareja temp = encontrado.get();
			temp.setNombre(nuevoDato.getNombre());
			temp.setEdad(nuevoDato.getEdad());
			temp.setExiste(nuevoDato.isExiste());
			
			parejaRepo.save(temp);
			return 0;
		} else if (encontrado.isEmpty() && nuevoDato != null) {
			parejaRepo.save(nuevoDato);
			return 1;
		} else {
			return 2;
		}
	}
	
	public int eliminarPorNombre(String nombre) {
		List<Pareja> encontrados = parejaRepo.findByNombre(nombre);
		if (encontrados.isEmpty()) {
			return 1;
		} else if (encontrados.size() > 1) {
			return 2;
		}
		
		parejaRepo.delete(encontrados.get(0));
		return 0;
	}
	
	public int eliminarPorEdad(int edad) {
		parejaRepo.deleteByEdad(edad);
		return 0;
	}
	
	

}
