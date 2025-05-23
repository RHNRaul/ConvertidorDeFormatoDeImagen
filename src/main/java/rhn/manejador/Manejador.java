package rhn.manejador;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rhn.convertir.Buscador;
import rhn.convertir.NewConvertidor;
import rhn.convertir.Salida;
import rhn.exceptions.BuscadorException;
import rhn.exceptions.ConvertidorException;
import rhn.exceptions.SalidaException;

public class Manejador {
	
	
	private static Manejador manejador;
	
	public static Manejador getInstance() {
		if (manejador == null) {
			manejador = new Manejador();
		}
		return manejador;
	}

	public void convertirArchivo(String direccion, String formatoImagen)
			throws BuscadorException, ConvertidorException, SalidaException {
		Buscador buscador = Buscador.getBuscador();
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		File archivo = buscador.buscaArchivo(direccion);
		Salida.salidaLocalUnico(archivo.getParent(), archivo.getName(), formatoImagen, convertidor.Convertir(archivo));
	}

	public void convertirArchivos(String direccion, String formatoImagen)
			throws BuscadorException, ConvertidorException, SalidaException {
		Buscador buscador = Buscador.getBuscador();
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		
		File[] archivos = buscador.buscarArchivos(direccion);
		
		
		ExecutorService executor = Executors.newCachedThreadPool();
		final ArrayList<Exception> excepciones = new ArrayList<>();
		for (File archivo : archivos) {
			executor.execute(() -> {
			    try {
			        Salida.salidaLocal(archivo.getParent(), archivo.getName(), formatoImagen, convertidor.Convertir(archivo));
			    } catch (ConvertidorException e) {
			        e.printStackTrace();
			        excepciones.add(e);
			    } catch(SalidaException e) {
			    	e.printStackTrace();
			        excepciones.add(e);
			    }
			});
		}
		try {
			System.out.println("Esperando a que se terminen de convertir los archivos...");
			executor.shutdown(); 
			executor.awaitTermination(0, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			executor.shutdownNow();
			throw new ConvertidorException("Error al esperar la finalización de los hilos.", e);
		}
		if (!excepciones.isEmpty()) {
			for (Exception e : excepciones) {
				System.out.println("Error: " + e.getMessage());
			}
			throw new SalidaException("Se produjeron errores durante la conversión de archivos.");
		}
		System.out.println("Se han convertido " + archivos.length + " archivos.");
	}

}
