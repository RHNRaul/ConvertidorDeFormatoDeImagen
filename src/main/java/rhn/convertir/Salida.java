package rhn.convertir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rhn.exceptions.SalidaException;

public class Salida {
	
	private static final String rutaSalida = "ImagenesConvertidas";

	
	public static void salidaLocal(String direccion,String nombreArchivo, String formato, BufferedImage imagen) throws SalidaException {
		try {
	        File directorio = new File(direccion + "\\"+rutaSalida+"-" + formato);
	        if (!directorio.exists()) {
	            directorio.mkdirs();
	        } 
	        File outputfile = new File(directorio, eliminaExtension(nombreArchivo) + "." + formato);			
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
