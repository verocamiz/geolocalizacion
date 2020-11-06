package servicio;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import config.VariablesGlobales;
import modelos.Idioma;
import modelos.Moneda;
import modelos.Pais;
import modelos.PaisDto;
import modelos.ZonaHoraria;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class restCountriesInformacionPais implements ProveedorInformacionPais {

	private static restCountriesInformacionPais instancia = null;
    private Retrofit retrofit;

    private restCountriesInformacionPais() { // singleton para instanciarlo una unica vez
        this.retrofit = new Retrofit.Builder()
                .baseUrl(VariablesGlobales.restcountriesUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static restCountriesInformacionPais instancia(){
        if(instancia== null){
            instancia = new restCountriesInformacionPais();
        }
        return instancia;
    }
    
  
	@Override
	public  Pais obtenerInformacionPais(String code) throws IOException{// deberia ser asincrono
		String url = VariablesGlobales.restcountriesUrl() +"alpha/" + code; 
		restCountriesInfoService service = this.retrofit.create(restCountriesInfoService.class);
		Call<PaisDto> req = service.obtenerInformacionPais(url);
		Response<PaisDto> response = req.execute(); //atributos publicos para mejorar la performance en caso de consultas concurrentes
		PaisDto paisDto = response.body();
		
		Pais pais = mapearPais(paisDto);
        return pais;
	}

	// delego el seteo del objeto que voy a retornar a un metodo
	private Pais mapearPais(PaisDto paisDto) {
		Pais pais = new Pais();
		List<Idioma> idiomas = new ArrayList<>();
		List<Moneda> monedas = new ArrayList<>();
		List<ZonaHoraria> zonas = new ArrayList<>();
		double lat = 0;
		double longitud = 0;
		
		
		paisDto.languages.stream().forEach((l)-> {
			   Idioma idioma = new Idioma (l.iso639_1,l.name,l.nativeName);
			   idiomas.add(idioma);
			});
		
		paisDto.currencies.stream().forEach((m)-> {
			   Moneda moneda = new Moneda (m.code,m.name,m.symbol);
			   monedas.add(moneda);
			});
		
		paisDto.timezones.stream().forEach((z)-> {
			   String hora = obtenerHora(z);
			   ZonaHoraria zona = new ZonaHoraria (z, hora);
			   zonas.add(zona);
			});
		
		
		if(paisDto.latlng.length == 2) {
			 lat = paisDto.latlng[0];
			 longitud = paisDto.latlng[1]; 
		}
		
		pais.setZonasHorarias(zonas);
		pais.setIdiomas(idiomas);
		pais.setMonedas(monedas);
		pais.setLatitud(lat);
		pais.setLongitud(longitud);
		
		
		return pais;
	}

	// el substring podria llegar a darme problemas si por alguna razon el formato fuera distinto,
	// esta parte se podria mejorar
	private String obtenerHora(String utc) {
		int hora = 0;
		int minutos = 0;
		
		if(!utc.equals("UTC")) {
			hora = Integer.parseInt(utc.substring(3, 6));
			 minutos = Integer.parseInt(utc.substring(7, 9));
		}
		
		Instant instant = Instant.now();
		
		Instant horaUTC = instant.plus(hora, ChronoUnit.HOURS).plus(minutos,
	        ChronoUnit.MINUTES);
		
		return horaUTC.toString().substring(11, 19);
		
		
	}

}
