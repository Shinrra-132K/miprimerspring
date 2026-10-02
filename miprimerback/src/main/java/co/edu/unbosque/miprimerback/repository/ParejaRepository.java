package co.edu.unbosque.miprimerback.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import co.edu.unbosque.miprimerback.entity.Pareja;

public interface ParejaRepository extends CrudRepository<Pareja, Long> {
	public void deleteByNombre(String nombre);
	public void deleteByEdad(int edad);
	List<Pareja> findByNombre(String nombre);
}
