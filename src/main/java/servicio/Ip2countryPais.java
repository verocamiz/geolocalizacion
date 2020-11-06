package servicio;

import java.io.IOException;

import config.VariablesGlobales;
import modelos.PaisNombre;
import modelos.PaisNombreDto;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Ip2countryPais implements ProveedorPais {

	private static Ip2countryPais instancia = null;
    private Retrofit retrofit;

    private Ip2countryPais() { // singleton para instanciarlo una unica vez
        this.retrofit = new Retrofit.Builder()
                .baseUrl(VariablesGlobales.ip2countryUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Ip2countryPais instancia(){
        if(instancia== null){
            instancia = new Ip2countryPais();
        }
        return instancia;
    }
    
	@Override
	public PaisNombre obtenerNombrePais(String ip) throws IOException{// deberia ser asincrono
		String url = VariablesGlobales.ip2countryUrl() +"/ip?" + ip; 
		Ip2countryPaisService service = this.retrofit.create(Ip2countryPaisService.class);
		Call<PaisNombreDto> req = service.obtenerNombrePais(url);
		Response<PaisNombreDto> response = req.execute(); //atributos publicos para mejorar la performance en caso de consultas concurrentes
		PaisNombreDto paisNombre = response.body();
		
		PaisNombre pais= new PaisNombre(paisNombre.countryCode,paisNombre.countryName,paisNombre.countryCode3);
		
        return pais;
	}
	
}
