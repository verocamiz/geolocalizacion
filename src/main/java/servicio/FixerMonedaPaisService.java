package servicio;

import modelos.MonedaDto;
import modelos.PaisNombreDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FixerMonedaPaisService {
	@GET
    Call<MonedaDto> obtenerCotizacion(@Url String url);
}
