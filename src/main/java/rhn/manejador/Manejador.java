package rhn.manejador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	
	private List<File> listaArchivos;
	
	
	public static Manejador getInstance() {
		if (manejador == null) {
			manejador = new Manejador();
		}
		return manejador;
	}
	
	public void buscarArchivos(String direccion)throws BuscadorException {
		Buscador buscador = Buscador.getBuscador();
		listaArchivos = buscador.listaArchivos(direccion);
	}

	public void convertirArchivo(String direccion, String formatoImagen)
			throws ConvertidorException, SalidaException {
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		File archivo = listaArchivos.get(0);
		Salida.salidaLocalUnico(archivo.getParent(), archivo.getName(), formatoImagen, convertidor.Convertir(archivo));
	}

	public void convertirArchivos(String direccion, String formatoImagen)
			throws ConvertidorException, SalidaException {
		ExecutorService executor = Executors.newCachedThreadPool();
		final ArrayList<Exception> excepciones = new ArrayList<>();
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		
		for(File file: listaArchivos) {
			executor.execute(() -> {
				try {
					Salida.salidaLocal(file.getParent(), file.getName(), formatoImagen, convertidor.Convertir(file));
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
			executor.awaitTermination(10, TimeUnit.SECONDS);
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
		System.out.println("Se han convertido " + listaArchivos.size() + " archivos.");
	}

	public List<File> getListaArchivos() {
		return listaArchivos;
	}
	
	
}
