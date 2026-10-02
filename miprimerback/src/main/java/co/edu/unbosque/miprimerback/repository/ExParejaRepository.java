package co.edu.unbosque.miprimerback.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.miprimerback.entity.ExPareja;

public interface ExParejaRepository extends CrudRepository<ExPareja, Long> {
	
	public void deleteByNombre(String nombre);
	
	public void deleteByEdad(int edad);
	
	List<ExPareja> findByNombre(String nombre);
	
}
