package co.edu.unbosque.miprimerback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.miprimerback.entity.ExPareja;
import co.edu.unbosque.miprimerback.repository.ExParejaRepository;

@Service
public class ExParejaService {
	
	@Autowired
	private ExParejaRepository exParejaRepo;
	
	public ExParejaService() {
		// TODO Auto-generated constructor stub
	}
	
	public long contar() {
		return exParejaRepo.count();
	}
	
	public boolean existe(long id) {
		return exParejaRepo.existsById(id);
	}
	
	public int crear(ExPareja nuevoDato) {
		try {
			exParejaRepo.save(nuevoDato);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}
	
	public List<ExPareja> mostrarTodo() {
		return (List<ExPareja>) exParejaRepo.findAll();
	}
	
	public int eliminarPorId(long id) {
		Optional<ExPareja> encontrado = exParejaRepo.findById(id);
		if (encontrado.isPresent()) {
			exParejaRepo.delete(encontrado.get());
			return 0;
		} else {
			return 1;
		}
	}
	
	public int actualizarPorId(long id, ExPareja nuevoDato) {
		Optional<ExPareja> encontrado = exParejaRepo.findById(id);
		if (encontrado.isPresent() && nuevoDato != null) {
			ExPareja temp = encontrado.get();
			temp.setNombre(nuevoDato.getNombre());
			temp.setEdad(nuevoDato.getEdad());
			temp.setMotivoSeparacion(nuevoDato.getMotivoSeparacion());
			
			exParejaRepo.save(temp);
			return 0;
		} else if (encontrado.isEmpty() && nuevoDato != null) {
			exParejaRepo.save(nuevoDato);
			return 1;
		} else {
			return 2;
		}
	}
	
	public int eliminarPorNombre(String nombre) {
		List<ExPareja> encontrados = exParejaRepo.findByNombre(nombre);
		if (encontrados.isEmpty()) {
			return 1;
		} else if (encontrados.size() > 1) {
			return 2;
		}
		
		exParejaRepo.delete(encontrados.get(0));
		return 0;
	}
	
	public int eliminarPorEdad(int edad) {
		exParejaRepo.deleteByEdad(edad);
		return 0;
	}
	
	

}
