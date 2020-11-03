package modelos;

import java.util.ArrayList;
import java.util.List;

public class PaisDto {
	public List<IdiomaDto> languages;
	public List<String> timezones;
//	public List<String> latlng = new ArrayList<>();
	public double[]  latlng;
	public List<MonedaDto> currencies; 
}
