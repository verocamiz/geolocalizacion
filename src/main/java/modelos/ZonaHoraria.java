package modelos;

public class ZonaHoraria {
	private String utc;
	private String hora;
	
	public ZonaHoraria() {
		
	}

	
	public ZonaHoraria(String utc, String hora) {
		setUtc(utc);
		setHora(hora);
		
	}

	public String getUtc() {
		return utc;
	}

	public void setUtc(String utc) {
		this.utc = utc;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
