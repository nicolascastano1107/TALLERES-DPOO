package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente {

	@Override
	public String getTipoCliente() {
		// TODO Auto-generated method stub
		return NATURAL;
	}

	@Override
	public String getIdentificador() {
		// TODO Auto-generated method stub
		return nombre;
	}
	
	public static String NATURAL = "Natural";
	private String nombre;
	public ClienteNatural (String nombre) {
		super();
		this.nombre = nombre;
	}
	
	

}
