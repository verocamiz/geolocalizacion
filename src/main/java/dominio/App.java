package dominio;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import Excepciones.InformacionPaisException;
import Excepciones.RastreadorIpException;
import Excepciones.fixerioReqException;
import modelos.Idioma;
import modelos.Pais;
import modelos.PaisInvocado;
import modelos.PaisNombre;
import modelos.PaisNombreDto;
import servicio.Ip2countryPais;
import servicio.ProveedorMonedaPais;
import servicio.fixerMonedaPais;
import servicio.restCountriesInformacionPais;

public class App {

	public static void main(String[] args) {
		
		RastreadorIP rastreador = new RastreadorIP(); // el rastreador deberia ser un singleton?
		RepoPaises repoPais = RepoPaises.instancia();
		
		String mensaje_opciones = "Ingrese el código de la operación que desea realizar:\n "
				+ "1 - Rastrear IP \n"
				+ "2 - Visualizar estadísticas\n"
				+ "Presione cualquier tecla para terminar la ejecución\n";
		
		

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
			System.out.println("Error en la conversión: " + e1.getMessage());
		}
		catch (InformacionPaisException e1) {
			System.out.println("Error al traer la información: " + e1);
		}
		catch (RastreadorIpException e1) {
			System.out.println("Error en el rastreador: " + e1);
		}
		
		
    }
	
	private static void mostrarInformacion(PaisInvocado paisInvocado, Pais pais, String ip) {
		System.out.println("====================================================================");
		System.out.println("Información");
		System.out.println("====================================================================");
		System.out.println("IP: " + ip + "\n"
				           + "Nombre: " + pais.getNombre().getNombre() + "\n"
				           + "Idiomas/Códigos ISO: " + pais.getIdiomasISO() + "\n"
				           + "Horas segun zona horaria: " + pais.getHoras() + "\n"
				           + "Moneda:  " + pais.getMonedasCotizacion() + "\n"
				           + "Distancia estimada: " +  String.format("%.2f", paisInvocado.getDistancia()) 
				           + " Kms");
		System.out.println("====================================================================\n");
		
	}

	public static void visualizarEstadisticas(RepoPaises repoPais) { 
		
		System.out.println("====================================================================");
		System.out.println("Estadísticas: Distancias desde la cual se realizaron las consultas");
		System.out.println("====================================================================");
		System.out.println("Distancia más lejana desde Bs. As.: " + "Brasil - 2862 km" + "\n"
				           + "Distancia más cercana desde Bs. As.: " + "Uruguay - 862 km" + "\n"
				           + "Distancia promedio de todas las ejecuciones del servicio: " + "1350 km" + "");
		System.out.println("====================================================================\n");
    }

}
