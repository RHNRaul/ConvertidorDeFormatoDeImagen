package rhn.convertir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

import rhn.exceptions.SalidaException;

public class Salida {
	
	private static final String rutaSalida = "ImagenesConvertidas";

	private static AtomicBoolean estaCreado = new AtomicBoolean(false);
	
	public static void salidaLocalUnico(String direccion,String nombreArchivo, String formato, BufferedImage imagen) throws SalidaException {
		try {
	        File outputfile = new File(direccion, eliminaExtension(nombreArchivo) + "Convertido." + formato);			
	        ImageIO.write(imagen, formato, outputfile);
		} catch (IOException e) {
			throw new SalidaException("Error al guardar la imagen: " + nombreArchivo, e);
		}
	}
	
	public static void salidaLocal(String direccion,String nombreArchivo, String formato, BufferedImage imagen) throws SalidaException {
		try {
	        File directorio = new File(direccion + "\\"+rutaSalida+"-" + formato);
	        if (!directorio.exists()&&!estaCreado.get()) {
	            directorio.mkdirs();
	            estaCreado.set(true);
	        } 
	        File outputfile = new File(directorio, eliminaExtension(nombreArchivo)+"."+formato);			
	        ImageIO.write(imagen, formato, outputfile);
		} catch (IOException e) {
			throw new SalidaException("Error al guardar la imagen: " + nombreArchivo, e);
		}
	}	
	
	private static String eliminaExtension(String nombreArchivo) {
		int index = nombreArchivo.lastIndexOf(".");
		if (index > 0) {
			nombreArchivo = nombreArchivo.substring(0, index);
		}
		return nombreArchivo;
	}
}
