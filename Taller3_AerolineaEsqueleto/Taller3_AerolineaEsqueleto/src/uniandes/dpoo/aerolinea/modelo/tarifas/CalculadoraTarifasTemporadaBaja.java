package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {

	
	private static final int COSTO_POR_KM_NATURAL     = 600;
    private static final int COSTO_POR_KM_CORPORATIVO = 900;

  
    private static final double DESCUENTO_PEQ      = 0.02;
    private static final double DESCUENTO_MEDIANAS = 0.10;
    private static final double DESCUENTO_GRANDES  = 0.20;

    @Override
    public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        if (vuelo == null || cliente == null) return 0;

        Ruta ruta = vuelo.getRuta();
        if (ruta == null) return 0;

        int distancia = Aeropuerto.calcularDistancia(ruta.getOrigen(), ruta.getDestino());
        if (distancia <= 0) return 0;

        
        if (cliente.getTipoCliente().equals(ClienteNatural.NATURAL)) {
            return distancia * COSTO_POR_KM_NATURAL;
        } 
        else if (cliente.getTipoCliente().equals(ClienteCorporativo.CORPORATIVO)) {
            return distancia * COSTO_POR_KM_CORPORATIVO;
        }

       
        return 0;
    }

    @Override
    public double calcularPorcentajeDescuento(Cliente cliente) {
        if (cliente == null) return 0.0;

        String tipo = cliente.getTipoCliente();

        switch (tipo) {
            case "natural":
                return DESCUENTO_PEQ;
            case "Corporativo":
                return DESCUENTO_MEDIANAS;
            default:
                return DESCUENTO_GRANDES;
        }
    }

}
