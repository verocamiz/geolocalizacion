package Excepciones;

public class RastreadorIpException extends RuntimeException  {
	
	private static final long serialVersionUID = 1L;

	public RastreadorIpException(String msg) {
		super(msg);
	}
}
