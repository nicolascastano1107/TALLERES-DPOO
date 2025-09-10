package uniandes.dpoo.aerolinea.modelo;

public class Avion {
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	private int capacidad;
	
	public Avion( String nombre, int capacidad) {
		this.nombre=nombre;
		this.capacidad=capacidad;
		
		
	}
	

}
