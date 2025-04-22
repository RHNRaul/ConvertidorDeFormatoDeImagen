package rhn.convertir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import rhn.exceptions.ConvertidorException; 

public class NewConvertidor {
	
	private static NewConvertidor convertidor;
	
	public NewConvertidor() {
	}
	
	public static NewConvertidor obtenerInstancia() {	
		if(convertidor == null) {
			convertidor = new NewConvertidor();
		}
		return convertidor;
	}
	
	public BufferedImage Convertir(File archivo) throws ConvertidorException {
		try {
			return ImageIO.read(archivo);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConvertidorException("Error al leer el archivo de imagen: " + archivo.getAbsolutePath(), e);
		}
	}
	
	public BufferedImage[] Convertir(File[] archivos) throws ConvertidorException {
		LinkedList<BufferedImage> listaImagenes = new LinkedList<>();
		for(File archivo : archivos) {
			listaImagenes.add(Convertir(archivo));
		}
		return listaImagenes.toArray(new BufferedImage[0]);
	}
	
	
	
	
}
