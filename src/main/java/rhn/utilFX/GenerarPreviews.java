package rhn.utilFX;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import rhn.exceptions.GenerarPreviewsException;

public class GenerarPreviews {

	private List<Image> imagenes;

	private static GenerarPreviews gp;

	public static GenerarPreviews getInstance() {
		if (gp == null) {
			gp = new GenerarPreviews();
		}
		return gp;
	}

	public List<Image> obtenerImages(List<File> archivos) throws GenerarPreviewsException {
		imagenes = new ArrayList<>();
		List<GenerarPreviewsException> excepciones = Collections.synchronizedList(new ArrayList<>());
		imagenes = archivos.parallelStream().map(archivo -> {
			try (FileInputStream input = new FileInputStream(archivo)) {
				return new Image(input);
			} catch (Exception e) {
				excepciones.add(new GenerarPreviewsException("Error al cargar la imagen: " + archivo.getName(), e));
				return null;
			}
		}).filter(imagen -> imagen != null).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);		
		if (!excepciones.isEmpty()) {
			throw new GenerarPreviewsException("Errores al generar las previsualizaciones", excepciones.get(0));
		}
		return imagenes;
	}

}
