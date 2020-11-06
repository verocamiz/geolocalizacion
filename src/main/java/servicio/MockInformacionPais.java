package servicio;

import java.io.IOException;

import modelos.Pais;

public class MockInformacionPais implements ProveedorInformacionPais{
	private Pais pais;

	@Override
	public Pais obtenerInformacionPais(String ip) throws IOException {
		return pais; 
	}
	
	public void setInformacionPais(Pais pais) {
		this.pais= pais;
	}
	
	public MockInformacionPais () {
		
	}
	
	public MockInformacionPais (Pais pais) {
		setInformacionPais(pais);
	}

}
