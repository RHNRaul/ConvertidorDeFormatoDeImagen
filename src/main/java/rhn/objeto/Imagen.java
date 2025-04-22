package rhn.objeto;

import java.io.File;

public class Imagen {
	
	private String ruta;
	private String nombre;
	private File archivo;
	
	
	public Imagen(String unaRuta,String unNombre,File unArchivo) {
		ruta = unaRuta;
		nombre = unNombre;
		archivo = unArchivo;
	}
	
	public Imagen() {
		
	}
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getNombre() {
		return nombre.replaceAll("\\.[^.]+$", "");
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	
	
	
	

}
