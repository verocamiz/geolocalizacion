package config;

public class VariablesGlobales {
	private static String ip2countryUrl = "https://api.ip2country.info";
	private static String restcountriesUrl = "https://restcountries.eu/rest/v2/";
	public static String fixerioUrl = "http://data.fixer.io/api/";
	private static double latitudBsAs = -34;
	private static double longitudBsAs = -64;
	
	public static String ip2countryUrl() {
		return ip2countryUrl;
	}
	
	public static String restcountriesUrl() {
		return restcountriesUrl;
	}
	
	public static double latitudBsAs(){
		return latitudBsAs;
	}
	
	public static double longitudBsAs(){
		return longitudBsAs;
	}

	public static String fixerioUrl() {
		return fixerioUrl;
	}
	
}
