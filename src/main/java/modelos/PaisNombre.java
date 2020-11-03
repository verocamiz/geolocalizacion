package modelos;

public class PaisNombre {
    private String codigo;
	private String nombre;
	private String codigo3;
	
	public PaisNombre (String _codigo,String _nombre,String _codigo3) {
		this.setCodigo(_codigo);
		this.setNombre(_nombre);
		this.setCodigo3(_codigo3);
	}
	public PaisNombre () {
		
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo3() {
		return codigo3;
	}
	public void setCodigo3(String codigo3) {
		this.codigo3 = codigo3;
	}
}
