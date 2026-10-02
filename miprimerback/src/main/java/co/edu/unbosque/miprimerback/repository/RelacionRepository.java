package co.edu.unbosque.miprimerback.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.miprimerback.entity.Relacion;

public interface RelacionRepository extends CrudRepository<Relacion, Long>{
	
	List<Relacion> findByNombrePersona1AndNombrePersona2(String nombrePersona1, String nombrePersona2);
	
	boolean existsByNombrePersona1OrNombrePersona2(String nombrePersona1, String nombrePersona2);
	
}
