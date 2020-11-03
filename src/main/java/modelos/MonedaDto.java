package modelos;

import Excepciones.Error;

public class MonedaDto {
	public String code;
	public String name;
	public String symbol;
	public RatesDto rates;
	public boolean success;
	public Error error;
}
