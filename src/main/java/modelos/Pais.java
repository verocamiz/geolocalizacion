package modelos;

import java.util.List;
import java.util.stream.Collectors;

public class Pais {

 // double distancia;
//  Integer cantidadInvocaciones;
  private PaisNombre nombre;
  //private List<String> horas; // ver que tipo va a tener esto
  private List<Idioma> idiomas;
  private List<ZonaHoraria> zonasHorarias;
  private double latitud;
  private double longitud;
  private List<Moneda> monedas;
  
 public Pais (List<Idioma> _idiomas,List<ZonaHoraria> _zonasHorarias,double _latitud, double _longitud, List<Moneda> _monedas){
		this.setIdiomas(_idiomas);
		this.setZonasHorarias(_zonasHorarias);
		this.setLatitud(_latitud);
		this.setLongitud(_longitud);
		this.setMonedas(_monedas);
 }
 
 public Pais(){
		
 }
  
  
public PaisNombre getNombre() {
	return nombre;
}
public void setNombre(PaisNombre nombre) {
	this.nombre = nombre;
}
public List<Idioma> getIdiomas() {
	return idiomas;
}
public void setIdiomas(List<Idioma> idiomas) {
	this.idiomas = idiomas;
}


public List<ZonaHoraria> getZonasHorarias() {
	return zonasHorarias;
}


public void setZonasHorarias(List<ZonaHoraria> zonasHorarias) {
	this.zonasHorarias = zonasHorarias;
}


public double getLatitud() {
	return latitud;
}


public void setLatitud(double latitud) {
	this.latitud = latitud;
}


public double getLongitud() {
	return longitud;
}


public void setLongitud(double longitud) {
	this.longitud = longitud;
}


public List<Moneda> getMonedas() {
	return monedas;
}


public void setMonedas(List<Moneda> monedas) {
	this.monedas = monedas;
}

public String getIdiomasISO() {
	return String.join(" ", idiomas.stream().map(x-> x.getNombre() + " (" + x.getIso639_1() + ") " ).collect(Collectors.toList()));
	
}

public String getHoras() {
		return String.join(" ", zonasHorarias.stream().map(x-> x.getHora() + "(" + x.getUtc() + ") " ).collect(Collectors.toList()));
		
	
}

public String getMonedasCotizacion() {
	return String.join(" ", monedas.stream().map(x->"1 "+ x.getCodigo() + " = " + x.getCotizacionDolar() + " USD" ).collect(Collectors.toList()));
} 
  
}
