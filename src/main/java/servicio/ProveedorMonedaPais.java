package servicio;

import java.io.IOException;

import modelos.Moneda;
import modelos.PaisNombre;

public interface ProveedorMonedaPais {
	
	public double obtenerCotizacion(String ip) throws IOException;
	
}
