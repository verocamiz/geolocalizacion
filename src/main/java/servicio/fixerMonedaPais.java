package servicio;

import java.io.IOException;

import config.VariablesGlobales;
import modelos.MonedaDto;
import modelos.RatesDto;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fixerMonedaPais implements ProveedorMonedaPais {

	private static fixerMonedaPais instancia = null;
    private Retrofit retrofit;

    private fixerMonedaPais() { // singleton para instanciarlo una unica vez
        this.retrofit = new Retrofit.Builder()
                .baseUrl(VariablesGlobales.fixerioUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static fixerMonedaPais instancia(){
        if(instancia== null){
            instancia = new fixerMonedaPais();
        }
        return instancia;
    }
    
	@Override
	public double obtenerCotizacion(String codigoMoneda) throws IOException{// deberia ser asincrono
		String url = VariablesGlobales.fixerioUrl() +"latest?access_key=1777d96ebf090a1b49fd103d1431b79e&base=" + codigoMoneda;
		FixerMonedaPaisService service = this.retrofit.create(FixerMonedaPaisService.class);
		Call<MonedaDto> req = service.obtenerCotizacion(url);
		Response<MonedaDto> response = req.execute(); //atributos publicos para mejorar la performance en caso de consultas concurrentes
     	MonedaDto monedaCotizacion = response.body();
		
		if(!monedaCotizacion.success) {
			String error = monedaCotizacion.error.type; 
			System.out.println("Error en la cotización: " + error);
			monedaCotizacion.rates = new RatesDto(); 
//			throw new fixerioReqException(error); 
		} else { 
			monedaCotizacion = response.body();
		}
		
        return monedaCotizacion.rates.USD;
	}

}
