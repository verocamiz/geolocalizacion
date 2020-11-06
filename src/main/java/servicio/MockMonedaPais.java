package servicio;

import java.io.IOException;

public class MockMonedaPais implements ProveedorMonedaPais {
	private double cotizacion;
	@Override
	public double obtenerCotizacion(String ip) throws IOException {
		
		return cotizacion;
	}
	
	public void setCotizacionDolar(double cotizacion) {
		this.cotizacion= cotizacion;
	}
	
	public MockMonedaPais () {
		
	}
	
	public MockMonedaPais (double cotizacion) {
		setCotizacionDolar(cotizacion);
	}

}
