package dominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Excepciones.InformacionPaisException;
import Excepciones.fixerioReqException;
import config.VariablesGlobales;
import modelos.Moneda;
import modelos.PaisInvocado;
import modelos.PaisNombre;
import servicio.ProveedorInformacionPais;
import servicio.ProveedorMonedaPais;
import servicio.fixerMonedaPais;
import servicio.restCountriesInformacionPais;

// las consultas a los service no son asincronicas, deberian serlo para contemplar el caso en que se hagan muchas peticiones en
// simultaneo, ademas de implementar un load balancer
public  class RepoPaises {
	
	private static RepoPaises instancia = null;
	// paisInvocado es una clase que por un lado tiene el pais con toda la informacion que trae de los service
	// y por otro tiene un contador con la cantidad de veces que fue invocado ese pais y la distancia
	private static List<PaisInvocado> paisesConsultados = new ArrayList<>();
	private static ProveedorMonedaPais proveedorMoneda = fixerMonedaPais.instancia();
	private static ProveedorInformacionPais proveedorInformacion = restCountriesInformacionPais.instancia();
	
    private RepoPaises() {
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
		
		// si el pais existe en la lista paisesConsultados, lo busco en la lista y me lo traigo
		if(existe) {
			paisInvocado = getPais(nombrePais.getCodigo3(),nombrePais);
			
		} 
		
		try {
			// obtengo el pais del service
			paisInvocado.setPais(proveedorInformacion.obtenerInformacionPais(nombrePais.getCodigo3()));
		} 
		catch (IOException e) {
			throw new InformacionPaisException(e.getMessage());
		}
		
		// obtengo la cotizacion
		obtenerCotizacion(paisInvocado.getPais().getMonedas());
		
		paisInvocado.aumentarCantidadInvocado();
		paisInvocado.getPais().setNombre(nombrePais);
		
		// si el pais no existia en la lista,   establezco la distancia calculada y lo agrego
		if(!existe){
			
			paisInvocado.setDistancia(obtenerDistancia(paisInvocado.getPais().getLatitud(),paisInvocado.getPais().getLongitud()));
			paisesConsultados.add(paisInvocado);
		} 
		
		// me guardo las ips que se consultaron
		if(!paisInvocado.getIps().stream().anyMatch(x -> x.equals(ip))){
		 paisInvocado.agregarIP(ip);
		} 
		
		
		return paisInvocado;
	}

	private static PaisInvocado getPais(String codigo3, PaisNombre nombrePais) {
		return paisesConsultados.stream().filter(p -> p.getPais().getNombre().getCodigo3().equals(nombrePais.getCodigo3()))
				                         .findFirst().orElse(null);

	}

	public static void obtenerCotizacion(List<Moneda> monedas) {
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

	public String getMasLejano() { // logica repetida, revisar
		String lejano = "---";
		
		if(!paisesConsultados.isEmpty()) {
			PaisInvocado pais = paisesConsultados.stream().max(Comparator.comparing(p -> p.getDistancia() )).get();
			lejano = pais.getPais().getNombre().getNombre()+" - " + String.format("%.2f", pais.getDistancia()) + " Kms";
		}
		
		return lejano;
	}

	public String getMasCercano() {
        String cercano = "---";
        
        if(!paisesConsultados.isEmpty()) {
		PaisInvocado pais = paisesConsultados.stream().min(Comparator.comparing(p -> p.getDistancia() )).get();
		cercano = pais.getPais().getNombre().getNombre()+" - " + String.format("%.2f", pais.getDistancia()) + " Kms";
        }
		
		return cercano;
	}

	public String getPromedio() {
		String promedio = "---";
		if(!paisesConsultados.isEmpty()) {
			double acumulador = sum(paisesConsultados.stream().map(x -> x.getDistancia() * x.getCantidadInvocado()).collect(Collectors.toList()));
			double cantInvocaciones = sum(paisesConsultados.stream().map(x ->   Double.valueOf(x.getCantidadInvocado())).collect(Collectors.toList()));
			promedio = String.format("%.2f", acumulador/cantInvocaciones)+ " Kms" ;
			 
		}
		
		return promedio;
		
	}
	
	public static  double sum(List<Double> distancias) {
		   double result = 0;
		   for (double value:distancias)
		     result += value;
		   return result;
	}
	

}

