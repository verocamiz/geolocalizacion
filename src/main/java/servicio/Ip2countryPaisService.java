package servicio;

import modelos.PaisNombreDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Ip2countryPaisService {
	@GET
    Call<PaisNombreDto> obtenerNombrePais(@Url String url);
}
