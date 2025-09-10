package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo {

	private String fecha;
	private Ruta ruta;
	private Avion avion;
	
	
	
	
	public Vuelo ( Ruta ruta, String fecha, Avion avion) {
		
	}
	public Ruta getRuta() {
		return ruta;
		
	}
	public String getFecha() {
		return fecha;
		
	}
	public Avion getAvion(){
		return avion;
		
	}
	public Collection<Tiquete> getTiquetes(){
		return null;
		
	}
	public int venderTiquetes( Cliente cliente, CalculadoraTarifas calculadora, int cantidad) {
		int vendidos = 0;

		for (int i = 0; i < cantidad; i++) {
		   
		    int valorTarifa = (int) calculadora.calcularTarifa(this, cliente);
		    Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, valorTarifa);

		    
		    GeneradorTiquetes.registrarTiquete(tiquete);
		    cliente.agregarTiquete(tiquete);

		    vendidos++;
		}

		
		if (vendidos >= 0) {
		    return vendidos;
		}
		return -1;
		
	}
	public boolean equals (Object obj){
		return false;
		
	}
	
}

