package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {
	
	private List<Tiquete> tiquetesSinUsar;
	public List<Tiquete> getTiquetesSinUsar() {
		return tiquetesSinUsar;
	}

	private List<Tiquete> tiquetesUsados;



	public Cliente() {
		 this.tiquetesSinUsar = new ArrayList<>();
	        this.tiquetesUsados  = new ArrayList<>();
	}
	public abstract String getTipoCliente();
	
	public abstract String getIdentificador();
		
		
	
	public void agregarTiquete (Tiquete tiquete) {
		if (tiquete == null) return;
		tiquetesSinUsar.add(tiquete);
		
	}
	public int calcularValorTotalTiquetes() {
		int total = 0;
        for (Tiquete t : tiquetesSinUsar) total += t.getTarifa();
        for (Tiquete t : tiquetesUsados)  total += t.getTarifa();
        return total;
		
	}
		
	public void usarTiquetes (Vuelo vuelo) {
		if (vuelo == null) return;

        
        List<Tiquete> aMovertiquet = new ArrayList<>();

        for (Tiquete tiquete : tiquetesSinUsar) {
            if (tiquete.getVuelo().equals(vuelo)) {
                tiquete.marcarComoUsado();
                aMovertiquet.add(tiquete);
            }
        }

       
        tiquetesSinUsar.removeAll(aMovertiquet);
    }
			
		
	}
	
	


