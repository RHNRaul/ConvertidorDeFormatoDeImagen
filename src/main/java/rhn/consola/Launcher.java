package rhn.consola;

import java.util.Scanner;

import rhn.convertir.Convertidor;

public class Launcher {
	public static void Main(String [] args) {
		int opcion;
		Scanner entrada = new Scanner(System.in);
		Convertidor co = new Convertidor();
		System.out.println("----BIENVENIDO----");
		System.out.println("----CONVERTIDOR DE IMAGENES----");
		System.out.println("Quieres convertir una imagen o por lote:");
		System.out.println("1.-Una Imagen");
		System.out.println("2.-Por Lote(Selecciona la carpeta)");
		System.out.print("Ingresa el numero: ");
		while(true) {
			try {
			opcion = entrada.nextInt();
			switch(opcion) {
			case 1:
				
				break;
			case 2:
				
				break;
			default: 
				continue;
			}
			break;
			}catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}

		System.out.println("Selecciona la carpeta de destino");
		
		
		
	}	
}
