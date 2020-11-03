package modelos;

public class Moneda {
	private String codigo;
	private String nombre;
	private String simbolo;
	private double cotizacionDolar;
	
	public Moneda(){
		
	}
	
	public Moneda(String _cod, String _nombre, String _simbolo){
		setCodigo(_cod);
		setNombre(_nombre);
		setSimbolo(_simbolo);
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getCotizacionDolar() {
		return cotizacionDolar;
	}

	public void setCotizacionDolar(double cotizacionDolar) {
		this.cotizacionDolar = cotizacionDolar;
	}
}
