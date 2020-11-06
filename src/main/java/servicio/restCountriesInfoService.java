package servicio;

import modelos.PaisDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface restCountriesInfoService {
	@GET
    Call<PaisDto> obtenerInformacionPais(@Url String url);
}
