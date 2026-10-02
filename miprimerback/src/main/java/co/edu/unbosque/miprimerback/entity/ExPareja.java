package co.edu.unbosque.miprimerback.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expareja")
public class ExPareja {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	@Column(unique = false, nullable = false)
	private String nombre;
	@Column(nullable = false)
	private int edad;
	@Column(nullable = false)
	private String motivoSeparacion;

	public ExPareja() {
		// TODO Auto-generated constructor stub
	}

	public ExPareja(String nombre, int edad, String motivoSeparacion) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.motivoSeparacion = motivoSeparacion;
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

	public String getMotivoSeparacion() {
		return motivoSeparacion;
	}

	public void setMotivoSeparacion(String motivoSeparacion) {
		this.motivoSeparacion = motivoSeparacion;
	}

	@Override
	public String toString() {
		return "ExPareja: \nId: " + id + "\nNombre: " + nombre + "\nEdad: " + edad + "\nMotivoSeparacion: "
				+ motivoSeparacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(edad), Long.valueOf(id), motivoSeparacion, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExPareja other = (ExPareja) obj;
		return edad == other.edad && id == other.id && Objects.equals(motivoSeparacion, other.motivoSeparacion)
				&& Objects.equals(nombre, other.nombre);
	}

}
