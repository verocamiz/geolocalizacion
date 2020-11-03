package modelos;

public class Idioma {
	private String iso639_1;
	private String nombre;
	private String nombreNativo;
	
	public Idioma (){
		
	}
	
	public Idioma (String _iso, String _nombre, String _nombreNativo){
		setIso639_1(_iso);
		setNombre(_nombre);
		setNombreNativo(_nombreNativo);
	}
	
	public String getIso639_1() {
		return iso639_1;
	}
	public void setIso639_1(String iso639_1) {
		this.iso639_1 = iso639_1;
	}
	public String getNombreNativo() {
		return nombreNativo;
	}
	public void setNombreNativo(String nombreNativo) {
		this.nombreNativo = nombreNativo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
