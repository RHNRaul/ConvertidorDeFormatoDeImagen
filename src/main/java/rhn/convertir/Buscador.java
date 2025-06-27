package rhn.convertir;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rhn.exceptions.BuscadorException;

public class Buscador {
	
	public static Buscador buscador;
	
	public Buscador() {
	}
	
	public static Buscador getBuscador() {
		if(buscador == null) {
			buscador = new Buscador();
		}
		return buscador;
	}
	
	
	public List<File> listaArchivos(String unaRuta) throws BuscadorException {
		ArrayList<File> lista = new ArrayList<>();
		
		if(unaRuta == null || unaRuta.isEmpty()) {
			throw new BuscadorException("La ruta del directorio no puede ser nula o vacía.",new NullPointerException());
		}
		File folder = new File(unaRuta);
		if(!folder.exists()) {
			throw new BuscadorException("El directorio no existe en la ruta especificada.",new NullPointerException());
		}
		if(folder.isFile()) {
			lista.add(folder);
		}
		if(folder.isDirectory()) {
			File[] listaDeArchivos = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.matches(ExpresionRegular.regex.getExpresion());
				}
			});
			
			if(listaDeArchivos == null || listaDeArchivos.length == 0) {
				throw new BuscadorException("No hay archivos en el directorio especificado.",new NullPointerException());
			}
			for(File file : listaDeArchivos) {
				lista.add(file);
			}
		}		
		return lista;
	}
	
	
	public File buscaArchivo(String rutaArchivo) throws BuscadorException{
		if(rutaArchivo == null || rutaArchivo.isEmpty()) {
			throw new BuscadorException("La ruta del directorio no puede ser nula o vacía.",new NullPointerException());
		}
		File file = new File(rutaArchivo);
		if(!file.isFile()) {
			throw new BuscadorException("La ruta especificada no es un archivo.",new NullPointerException());
		}
		
		if(!file.exists()) {
			throw new BuscadorException("El archivo no existe en la ruta especificada.",new NullPointerException());
		}
		
//		if(!file.getName().matches(ExpresionRegular.regex.getExpresion())) {
//			throw new BuscadorException("El archivo es una imagen invalida.");
//		}
		return file;
	}
	
	
	public File[] buscarArchivos(String rutaDirectorio) throws BuscadorException
	{
		if(rutaDirectorio == null || rutaDirectorio.isEmpty()) {
			throw new BuscadorException("La ruta del directorio no puede ser nula o vacía.",new NullPointerException());
		}		
		File folder = new File(rutaDirectorio);
		File[] listaDeArchivos = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(ExpresionRegular.regex.getExpresion());
			}
		});
		
		if(listaDeArchivos == null || listaDeArchivos.length == 0) {
			throw new BuscadorException("No hay archivos en el directorio especificado.",new NullPointerException());
		}
	
		return listaDeArchivos;
	}	
	
	private enum ExpresionRegular{
		regex("^.*\\.(jpg|jpeg|png|bmp|tiff|svg|webp|jfif)$");	
		private String expresion;	
		private ExpresionRegular(String expresion) {
			this.expresion = expresion;
		}
		public String getExpresion() {
			return expresion;
		}
	}

}
