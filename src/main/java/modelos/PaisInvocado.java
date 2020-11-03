package modelos;

import java.util.ArrayList;
import java.util.List;

public class PaisInvocado {
	private Pais pais;
	private double distancia;
	private int cantidadInvocado = 0;
	private List<String> ips = new ArrayList<>();
	
	public PaisInvocado(Pais _pais, double _distancia, int _cantidadInvocado){
		this.setPais(_pais);
		this.setDistancia(_distancia);
		this.setCantidadInvocado(_cantidadInvocado);
	}
	
	public PaisInvocado() {
		
	}

	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public int getCantidadInvocado() {
		return cantidadInvocado;
	}
	public void setCantidadInvocado(int cantidadInvocado) {
		this.cantidadInvocado = cantidadInvocado;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public void aumentarCantidadInvocado() {
		this.cantidadInvocado += 1;
	}

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}
	
	public void agregarIP (String ip){
		this.ips.add(ip);
	}
}
