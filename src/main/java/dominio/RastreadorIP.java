package dominio;

import java.io.IOException;

import Excepciones.InformacionPaisException;
import Excepciones.RastreadorIpException;
import Excepciones.fixerioReqException;
import modelos.Pais;
import modelos.PaisInvocado;
import modelos.PaisNombre;
import servicio.Ip2countryPais;
import servicio.ProveedorPais;
import servicio.restCountriesInformacionPais;

public class RastreadorIP {
 ProveedorPais proveedorPais = Ip2countryPais.instancia();
 RepoPaises repoPaises = RepoPaises.instancia();
 
 public PaisInvocado rastrearIP(String ip) {
	 
	 validarIp(ip);
	 
	 PaisInvocado pais = new PaisInvocado ();
	 
	 try {
		PaisNombre nombrePais = proveedorPais.obtenerNombrePais(ip);
		pais = RepoPaises.obtenerPais(nombrePais,ip);
		
		
	} catch (InformacionPaisException e) {
		throw new InformacionPaisException(e.getMessage());
	} 
	 catch (fixerioReqException e) {
		 throw new fixerioReqException(e.getMessage());
	} 
	 catch (IOException e) {
		 throw new RastreadorIpException(e.getMessage());
	}
	 
	 return pais;
 }

private void validarIp(String ip) {
	if (false) {// validar el formato
		throw new RastreadorIpException("El formato de IP ingresado es incorrecto");
	}
	
}
}
