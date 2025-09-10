package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {

	@Override
	protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		// TODO Auto-generated method stub
		 if (vuelo == null || cliente == null) {
	            return 0;
	        }

	        Ruta ruta = vuelo.getRuta();
	        if (ruta == null) {
	            return 0;
	        }

	      
	        Aeropuerto origen = ruta.getOrigen();
	        Aeropuerto destino = ruta.getDestino();
	        int km = Aeropuerto.calcularDistancia(origen, destino);

	       
	        if (km <= 0) {
	            return 0;
	        }

	      
	        return km * COSTO_POR_KM;
	    }
	

	@Override
	protected double calcularPorcentajeDescuento(Cliente cliente) {
		// TODO Auto-generated method stub
		return 0.0;
	}
	protected int COSTO_POR_KM = 1000;
	

}
