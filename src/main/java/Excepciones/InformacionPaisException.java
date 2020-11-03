package Excepciones;


public class InformacionPaisException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InformacionPaisException(String msg) {
		super(msg);
	}
}
