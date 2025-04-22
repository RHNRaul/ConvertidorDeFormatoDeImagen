package rhn.manejador;

import java.io.File;

import rhn.convertir.Buscador;
import rhn.convertir.NewConvertidor;
import rhn.convertir.Salida;
import rhn.exceptions.BuscadorException;
import rhn.exceptions.ConvertidorException;
import rhn.exceptions.SalidaException;

public class Manejador {
		
	public void convertirArchivo(String direccion,String formatoImagen) throws BuscadorException, ConvertidorException, SalidaException {
		Buscador buscador = Buscador.getBuscador();
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		File archivo = buscador.buscaArchivo(direccion);
		Salida.salidaLocal(archivo.getParent(),archivo.getName(),formatoImagen,convertidor.Convertir(archivo));
	}
	
	public void convertirArchivos(String direccion,String formatoImagen) throws BuscadorException, ConvertidorException,SalidaException {
		Buscador buscador = Buscador.getBuscador();
		NewConvertidor convertidor = NewConvertidor.obtenerInstancia();
		File[] archivos = buscador.buscarArchivos(direccion);
		for(File archivo : archivos) {
			try {
				Salida.salidaLocal(archivo.getParent(),archivo.getName(),formatoImagen,convertidor.Convertir(archivo));
			} catch (SalidaException e) {
				e.printStackTrace();
			}
		}
	}
	
}
