package servicio;

import java.io.IOException;

import modelos.Pais;
import modelos.PaisNombre;
import modelos.PaisNombreDto;

public interface ProveedorPais {
  public PaisNombre obtenerNombrePais(String ip) throws IOException;
}
