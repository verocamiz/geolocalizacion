package Excepciones;

public class NombrePaisExeption extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NombrePaisExeption(String msg) {
		super(msg);
	}
}
