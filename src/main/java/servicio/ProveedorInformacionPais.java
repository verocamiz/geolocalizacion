package servicio;

import java.io.IOException;

import modelos.Pais;

public interface ProveedorInformacionPais {
	public  Pais obtenerInformacionPais(String ip) throws IOException;
}
