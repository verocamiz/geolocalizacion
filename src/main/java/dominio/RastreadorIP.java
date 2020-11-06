package dominio;

import java.io.IOException;

import com.google.common.net.InetAddresses;

import Excepciones.InformacionPaisException;
import Excepciones.RastreadorIpException;
import Excepciones.fixerioReqException;
import modelos.PaisInvocado;
import modelos.PaisNombre;
import servicio.Ip2countryPais;
import servicio.ProveedorPais;

public class RastreadorIP {// podria llegar a ser un singleton para este caso tambien
	
 ProveedorPais proveedorPais = Ip2countryPais.instancia();
 RepoPaises repoPaises = RepoPaises.instancia();
 
 public PaisInvocado rastrearIP(String ip) {
	 
	 // utilizo guava para validar el formato de la ip que ingresa el usuario
	 validarIp(ip);
	 
	 PaisInvocado pais = new PaisInvocado ();
	 
	 try {
		 // llamo a la clase encargada de obtener el nombre del pais
		PaisNombre nombrePais = proveedorPais.obtenerNombrePais(ip);
		
		// decidi crear un repositorio desde donde puedo acceder a todas las consultas hechas, para este ejercicio
		// lo guarde en memoria pero lo ideal seria persistirlo
		// el repo va a tener toda la logica asociada a procesar los datos de los objetos que va guardando en memoria
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

public void validarIp(String ip) {
	if (!InetAddresses.isInetAddress(ip)) {
		throw new RastreadorIpException("El formato de IP ingresado es incorrecto");
	} 
	
}
}
