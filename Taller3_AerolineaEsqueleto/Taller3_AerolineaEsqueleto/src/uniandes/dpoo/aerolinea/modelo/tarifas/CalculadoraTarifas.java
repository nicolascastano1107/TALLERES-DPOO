package uniandes.dpoo.aerolinea.modelo.tarifas;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public abstract class CalculadoraTarifas {

	
	
	public double IMPUESTO = 0.28;
	public int calcularTarifa (Vuelo vuelo, Cliente cliente) {
		
		
		  int costoBase = calcularCostoBase(vuelo, cliente);
	        double descuento = calcularPorcentajeDescuento(cliente);

	        int elImpuesto = (int)(costoBase - costoBase * descuento);

	        int impuestos = calcularValorImpuestos(elImpuesto);

	        return elImpuesto + impuestos;
	    }

		
	
	protected abstract int calcularCostoBase (Vuelo vuelo, Cliente cliente);
	// return entre 0 y 1 
	
	protected abstract double calcularPorcentajeDescuento (Cliente cliente);
	
	
	protected int calcularDistanciaVuelo( Ruta ruta) {
		return Aeropuerto.calcularDistancia(ruta.getOrigen(), ruta.getDestino());
	}
	protected int calcularValorImpuestos (int costoBase) {
		 return (int)(costoBase * IMPUESTO);
	}
	
	
	
}
