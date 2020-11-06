package servicio;

import java.io.IOException;

import modelos.PaisNombre;

public class MockPais implements ProveedorPais{
	private PaisNombre nombre;
	@Override
	public PaisNombre obtenerNombrePais(String ip) throws IOException {
		return nombre;
	}
	

	public void setNombrePais(PaisNombre nombre) {
		this.nombre= nombre;
	}
	
	public MockPais () {
		
	}
	
	public MockPais (PaisNombre nombre) {
		setNombrePais(nombre);
	}

}
