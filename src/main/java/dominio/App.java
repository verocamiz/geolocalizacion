package dominio;

import java.util.Scanner;

import Excepciones.InformacionPaisException;
import Excepciones.RastreadorIpException;
import Excepciones.fixerioReqException;
import modelos.Pais;
import modelos.PaisInvocado;

public class App {
	// trato de que en esta parte haya la menor cantidad de l�gica posible, decid� delegar las excepciones hasta el �ltimo nivel
	// en este caso solo hace un print de la excepcion

	public static void main(String[] args) {
		
		RastreadorIP rastreador = new RastreadorIP();
		RepoPaises repoPais = RepoPaises.instancia();
		
		String mensaje_opciones = "Ingrese el c�digo de la operaci�n que desea realizar:\n "
				+ "1 - Rastrear IP \n"
				+ "2 - Visualizar estad�sticas\n"
				+ "Presione cualquier tecla para terminar la ejecuci�n\n";
		
		

		System.out.println(mensaje_opciones);
		
		Scanner sc = new Scanner(System.in);
		String codigo_operacion = sc.nextLine();
		
		
   		while (validarCodigo(codigo_operacion)){ 
			switch (codigo_operacion) {
            case "1":
                rastrearIP(rastreador);
                break;
            case "2":
                visualizarEstadisticas(repoPais);
                break;
            default:
                break;
                
        }
 			if(validarCodigo(codigo_operacion)) {
			System.out.println(mensaje_opciones);
			codigo_operacion = sc.nextLine();
 		   }
   		
		}
   		
   		//sc.close();
		
	}

	private static boolean validarCodigo(String codigo_operacion) {
		
		if(codigo_operacion.matches("[0-9]+")) {
			return Integer.parseInt(codigo_operacion) != 3;
		} else  {
	          return false;
		}
	}

	public static void rastrearIP(RastreadorIP rastreador) { 
		System.out.println("Ingrese la IP:" );
		Scanner sc = new Scanner(System.in);
		String ip = sc.nextLine();
		
		PaisInvocado paisInvocado = new PaisInvocado();
		
		
		
		try {
			paisInvocado = rastreador.rastrearIP(ip);
			Pais pais = paisInvocado.getPais();
			
			mostrarInformacion(paisInvocado,pais,ip);
		} catch (fixerioReqException e1) {
			System.out.println("Error en la conversi�n: " + e1.getMessage());
		}
		catch (InformacionPaisException e1) {
			System.out.println("Error al traer la informaci�n: " + e1);
		}
		catch (RastreadorIpException e1) {
			System.out.println("Error en el rastreador: " + e1);
		}finally {
//			sc.close();
		}
		
		
    }
	
	private static void mostrarInformacion(PaisInvocado paisInvocado, Pais pais, String ip) {
		System.out.println("====================================================================");
		System.out.println("Informaci�n");
		System.out.println("====================================================================");
		System.out.println("IP: " + ip + "\n"
				           + "Nombre: " + pais.getNombre().getNombre() + "\n"
				           + "Idiomas/C�digos ISO: " + pais.getIdiomasISO() + "\n"
				           + "Horas segun zona horaria: " + pais.getHoras() + "\n"
				           + "Moneda:  " + pais.getMonedasCotizacion() + "\n"
				           + "Distancia estimada: " +  String.format("%.2f", paisInvocado.getDistancia()) 
				           + " Kms");
		System.out.println("====================================================================\n");
		
	}

	public static void visualizarEstadisticas(RepoPaises repoPais) { 
		
		System.out.println("====================================================================");
		System.out.println("Estad�sticas: Distancias desde la cual se realizaron las consultas");
		System.out.println("====================================================================");
		System.out.println("Distancia m�s lejana desde Bs. As.: " + repoPais.getMasLejano() + "\n"
				           + "Distancia m�s cercana desde Bs. As.: " +repoPais.getMasCercano() + "\n"
				           + "Distancia promedio de todas las ejecuciones del servicio: " +  repoPais.getPromedio() + "");
		System.out.println("====================================================================\n");
    }

}
