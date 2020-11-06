package servicio;

import java.io.IOException;

import modelos.Pais;

// creo un adapter que me de la posibilidad de usar otro servicio en caso de ser necesario
// tambien me permite el uso de mocks para mejorar la testeabilidad
public interface ProveedorInformacionPais {
	public  Pais obtenerInformacionPais(String ip) throws IOException;
}
