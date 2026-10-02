package co.edu.unbosque.miprimerback.entity;

import java.lang.Object;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "relacion")
public class Relacion {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	@Column(unique = false, nullable = false)
	private String nombrePersona1;
	@Column(unique = false, nullable = false)
	private String nombrePersona2;

	public Relacion() {
		// TODO Auto-generated constructor stub
	}

	public Relacion(String nombrePersona1, String nombrePersona2) {
		super();
		this.nombrePersona1 = nombrePersona1;
		this.nombrePersona2 = nombrePersona2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombrePersona1() {
		return nombrePersona1;
	}

	public void setNombrePersona1(String nombrePersona1) {
		this.nombrePersona1 = nombrePersona1;
	}

	public String getNombrePersona2() {
		return nombrePersona2;
	}

	public void setNombrePersona2(String nombrePersona2) {
		this.nombrePersona2 = nombrePersona2;
	}

	@Override
	public String toString() {
		return "Relacion: \nId: " + id + "\nPersona 1: " + nombrePersona1 + "\nPersona 2: " + nombrePersona2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Long.valueOf(id), nombrePersona1, nombrePersona2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relacion other = (Relacion) obj;
		return id == other.id && Objects.equals(nombrePersona1, other.nombrePersona1)
				&& Objects.equals(nombrePersona2, other.nombrePersona2);
	}
	
	

}
