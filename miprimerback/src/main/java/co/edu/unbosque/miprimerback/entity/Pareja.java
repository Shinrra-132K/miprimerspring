package co.edu.unbosque.miprimerback.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pareja")
public class Pareja {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	@Column(unique = false, nullable = false)
	private String nombre;
	@Column(nullable = false)
	private int edad;
	@Column(nullable = false)
	private boolean existe;

	public Pareja() {
		// TODO Auto-generated constructor stub
	}

	public Pareja(String nombre, int edad, boolean existe) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.existe = existe;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	@Override
	public String toString() {
		return "Pareja: \nId: " + id + "\nNombre: " + nombre + "\nEdad: " + edad + "\nExiste: " + existe;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(edad), Boolean.valueOf(existe), Long.valueOf(id), nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pareja other = (Pareja) obj;
		return edad == other.edad && existe == other.existe && id == other.id && Objects.equals(nombre, other.nombre);
	}

}
