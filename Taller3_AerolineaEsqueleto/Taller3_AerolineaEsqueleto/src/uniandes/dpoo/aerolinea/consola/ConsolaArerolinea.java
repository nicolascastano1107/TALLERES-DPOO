package uniandes.dpoo.aerolinea.consola;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;

public class ConsolaArerolinea extends ConsolaBasica
{
    private Aerolinea unaAerolinea;

    /**
     * Es un método que corre la aplicación y realmente no hace nada interesante: sólo muestra cómo se podría utilizar la clase Aerolínea para hacer pruebas.
     */
    public void correrAplicacion( )
    {
        try
        {
            unaAerolinea = new Aerolinea( );
            sembrarRutasMinimas(unaAerolinea);
          
            String archivo = "tiquetes.json"; 
            unaAerolinea.cargarTiquetes(archivo, CentralPersistencia.JSON);

            System.out.println("OK -> clientes: " + unaAerolinea.getClientes().size());
            System.out.println("OK -> rutas: "    + unaAerolinea.getRutas().size());
            System.out.println("OK -> tiquetes: " + unaAerolinea.getTiquetes().size());
        } catch (TipoInvalidoException | IOException | InformacionInconsistenteException e) {
            e.printStackTrace();
        }
    }
        private void sembrarRutasMinimas(Aerolinea a) {
            
            Aeropuerto bog = new Aeropuerto("El Dorado", "BOG", "Bogotá",    4.701, -74.146);
            Aeropuerto mia = new Aeropuerto("Miami Intl", "MIA", "Miami",  25.793, -80.290);
            
        Ruta r4558 = new Ruta(bog, mia, "08:00", "13:00", "4558");
        a.agregarRuta(r4558);
        
    }
    public static void main( String[] args )
    {
        ConsolaArerolinea ca = new ConsolaArerolinea( );
        ca.correrAplicacion( );
    }
}