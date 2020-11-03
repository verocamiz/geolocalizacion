package dominio;

import java.util.List;

import Excepciones.InformacionPaisException;
import Excepciones.fixerioReqException;
import config.VariablesGlobales;
import modelos.Idioma;
import modelos.Moneda;
import modelos.MonedaDto;
import modelos.Pais;
import modelos.PaisInvocado;
import modelos.PaisNombre;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import servicio.Ip2countryPais;
import servicio.ProveedorInformacionPais;
import servicio.ProveedorMonedaPais;
import servicio.ProveedorPais;
import servicio.fixerMonedaPais;
import servicio.restCountriesInformacionPais;

import java.io.IOException;
import java.util.ArrayList;

public  class RepoPaises {
	
	private static RepoPaises instancia = null;
	private static List<PaisInvocado> paisesConsultados;
	private static ProveedorMonedaPais proveedorMoneda = fixerMonedaPais.instancia();
	private static ProveedorInformacionPais proveedorInformacion = restCountriesInformacionPais.instancia();
	
    private RepoPaises() { // singleton para instanciarlo una unica vez
        RepoPaises.paisesConsultados = new ArrayList<>();
    }

    public static RepoPaises instancia(){
        if(instancia== null){
            instancia = new RepoPaises();
        }
        return instancia;
    }

	public static PaisInvocado obtenerPais(PaisNombre nombrePais,String ip)  {
		PaisInvocado paisInvocado = new PaisInvocado();
		
		boolean existe =paisesConsultados.stream().anyMatch(p -> p.getPais().getNombre().getCodigo3().equals( nombrePais.getCodigo3()));
		
		if(existe) {
			paisInvocado = getPais(nombrePais.getCodigo3(),nombrePais);
			System.out.println("si existe el pais en la lista");
			
		} else {
			System.out.println("no existe el pais en la lista");
			
		} 
		
		try {
			paisInvocado.setPais(proveedorInformacion.obtenerInformacionPais(nombrePais.getCodigo3()));
		} 
		catch (IOException e) {
			throw new InformacionPaisException(e.getMessage());
		}
		
		obtenerCotizacion(paisInvocado.getPais().getMonedas());
		
		paisInvocado.aumentarCantidadInvocado();
		paisInvocado.getPais().setNombre(nombrePais);
		
		if(!existe){
			
			paisInvocado.setDistancia(obtenerDistancia(paisInvocado.getPais().getLatitud(),paisInvocado.getPais().getLongitud()));
			paisesConsultados.add(paisInvocado);
		} 
		
		if(!paisInvocado.getIps().stream().anyMatch(x -> x.equals(ip))){
		 paisInvocado.agregarIP(ip);
		} 
		
		
		return paisInvocado;
		// veo si existe el pais en la lista, si existe lo traigo de la lista
		//despues tengo que ir a la api a buscar el pais la cotizacion y la distancia
		// el pais y la cotizacion cambiar, la distancia no entonces si ya existe no necesito la distancia
	}

	private static PaisInvocado getPais(String codigo3, PaisNombre nombrePais) {
		return paisesConsultados.stream().filter(p -> p.getPais().getNombre().getCodigo3().equals(nombrePais.getCodigo3()))
				                         .findFirst().orElse(null);

	}

	public static void obtenerCotizacion(List<Moneda> monedas) {
		// para cada moneda llamo al servicio para que calcule la cotizacion y la seteo
		monedas.stream().forEach((m)-> {
			try {
				m.setCotizacionDolar(proveedorMoneda.obtenerCotizacion(m.getCodigo()));
			} catch (IOException e) {
				throw new fixerioReqException(e.getMessage());
			}
			});
	}

	public static double obtenerDistancia(double latitud,double longitud) {
		 double distancia = calcularDistancia(VariablesGlobales.latitudBsAs(),latitud,VariablesGlobales.longitudBsAs(),
				 longitud,0.0,0.0);
		double distanciaKm = distancia / 1000;
		 
		 return distanciaKm;
		
	}
	
	public static double calcularDistancia(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; 

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000;

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}

}
