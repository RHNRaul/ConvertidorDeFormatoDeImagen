package rhn.convertir;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import rhn.objeto.Imagen;

public class Convertidor {

	private String rutaDeDestino;
	private ArrayList<Imagen> listaImagenes = new ArrayList<>(  );
	
	
	
	
	public void escogerImagen(JFrame pantalla) {
			FileDialog dialog = new FileDialog(pantalla, "Selecciona tu imagen");
			dialog.setMode(FileDialog.LOAD);
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			String file = dialog.getFile();
			if (!validarArchivo(file)) {
				dialog.dispose();
				return;
			}
			listaImagenes.add(new Imagen(dialog.getDirectory(),dialog.getFile(),dialog.getFiles()[0]));
			dialog.dispose();
	}
	
		
	private boolean validarArchivo(String validar) {
		String expresionRegular = "^.*\\.(jpg|jpeg|png|gif|bmp|tiff|svg|webp|jfif)$";
        Pattern pattern = Pattern.compile(expresionRegular);
        Matcher matcher = null;
        try {
            matcher = pattern.matcher(validar);
        }catch(NullPointerException e){
        	System.out.println("Nulo");
        	return false;
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
        return matcher.matches();
	}
	
	
	public void porLotes() {
		File folder = new File("/Users/you/folder/");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
	}
	
	
	public void escogerPorLotes(JFrame pantalla) {
	
		crearPantallaTemporal(pantalla);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Documents"));
		chooser.setDialogTitle("SELECCIONA LA CARPETA QUE CONTIENE LAS IMAGENES");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
        int escogio = chooser.showOpenDialog(pantalla);
        if(escogio == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            System.out.println("Carpeta seleccionada: " + selectedFolder.getAbsolutePath());
            // Obtener todos los archivos en la carpeta seleccionada
            File[] files = selectedFolder.listFiles();
            for(File img : files) {
            	if(validarArchivo(img.getName())) {
            	listaImagenes.add(new Imagen(img.getPath(),img.getName(),img));      	
            	}
            }        
            cerrarPantallaConsola(pantalla);
            System.out.println(listaImagenes.size());
        }		
	}
	
	public String escogerCarpeta(JFrame pantalla) {
		String rutadestino = null;
		while (true) {
			if (pantalla != null) {
				pantalla.setAlwaysOnTop(true); // Asegura que esté al frente
				pantalla.setVisible(true);
				pantalla.toFront(); // Lleva el JFrame al frente de otras ventanas
			}
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("SELECCIONA TU CARPETA DE DESTINO");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			int electo = chooser.showOpenDialog(pantalla);
			if (!(electo == JFileChooser.APPROVE_OPTION)) {
				chooser = null;
				continue;
			}
			
			File tempruta =  chooser.getSelectedFile();
			System.out.println(tempruta);
			rutadestino = tempruta.getPath();
			pantalla.dispose();
			chooser = null;
			break;
		}
		return rutadestino;
	}
	
	public void escogerDestino(JFrame unaPantalla) {
		rutaDeDestino = escogerCarpeta(unaPantalla);
	}
	
	private void cerrarPantallaConsola(JFrame unaPantalla) {
		if (unaPantalla != null)
			unaPantalla.dispose();
	}
	
	private void crearPantallaTemporal(JFrame unaPantalla) {
		if(unaPantalla != null ) {
			unaPantalla.setAlwaysOnTop(true);
			unaPantalla.setVisible(true);
			unaPantalla.toFront(); 
		}
	}
	
	public boolean convertirImagen(String formato) {
		for (Imagen imagen : listaImagenes) {
			try {
				File input = imagen.getArchivo();
				File output = new File(rutaDeDestino + "\\" + imagen.getNombre()+formato+ "." + formato);
				BufferedImage image = ImageIO.read(input);
				BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
				ImageIO.write(result, formato, output);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
