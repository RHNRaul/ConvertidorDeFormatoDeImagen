package rhn.controlador;

import java.io.File;
import java.util.List;

import org.controlsfx.control.ToggleSwitch;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rhn.exceptions.BuscadorException;
import rhn.exceptions.GenerarPreviewsException;
import rhn.manejador.Manejador;
import rhn.utilFX.GenerarPreviews;

public class PantallaFXMLController {

	@FXML
	StackPane stackPanePrincipal;
	@FXML
	GridPane gridPanePrincipal;
	@FXML
	StackPane stackPaneIzquierdo;
	@FXML
	StackPane stackPaneDerecho;
	@FXML
	ScrollPane scrollPaneDerecho;
	@FXML
	VBox vboxDerecho;
	@FXML
	ToggleSwitch switchModo;
	@FXML
	Label lblSelecciona;
	@FXML
	Button btnSeleccionaArchivo;
	@FXML
	ComboBox<String> cmbFormato;
	@FXML
	Button btnConvertir;

	private String rutaArchivo = "";
	private static Stage escena;

	@FXML
	public void initialize() {
		events();
	}

	public static void lanzaPantallaPrincipal(Stage escenario) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(
					PantallaFXMLController.class.getResource("../../pantalla/PantallaFXML.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			escena = escenario;
			escena.setTitle("Convertidor de imagenes");
			escena.setScene(scene);
			escena.setResizable(false);
			escena.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void convertir() {
		if (rutaArchivo == null || rutaArchivo.isEmpty()) {
			lanzaAlert(AlertType.ERROR, "Error", "No hay archivo o carpeta seleccionada");
			return;
		}
		if (cmbFormato.getValue() == null) {
			lanzaAlert(AlertType.ERROR, "Error", "No hay formato seleccionado");
			return;
		}
		try {
			Manejador manejador = Manejador.getInstance();
			if (switchModo.isSelected()) {
				manejador.convertirArchivos(rutaArchivo, cmbFormato.getValue());
				lanzaAlert(AlertType.INFORMATION, "Exito",
						"Se han convertido todos los archivos de la carpeta: " + rutaArchivo);
			} else {
				manejador.convertirArchivo(rutaArchivo, cmbFormato.getValue());
				lanzaAlert(AlertType.INFORMATION, "Exito", "Se ha convertido el archivo: " + rutaArchivo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			lanzaAlert(AlertType.ERROR, "Error", "No se ha podido convertir el archivo o archivos: " + rutaArchivo);
		}

	}

	private void lanzaAlert(AlertType tipoAlerta, String titulo, String mensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(titulo);
		alerta.setHeaderText(mensaje);
		alerta.showAndWait();
	}

	private void cargaPreview() {
		Manejador manejador = Manejador.getInstance();
		if (rutaArchivo == null || rutaArchivo.isEmpty()) {
			lanzaAlert(AlertType.ERROR, "Error", "No hay archivo o carpeta seleccionada");
			return;
		}
			try {
				manejador.buscarArchivos(rutaArchivo);
				GenerarPreviews gp = GenerarPreviews.getInstance();
				Platform.runLater(() -> {
					try {
						cargarImagenesPreview(gp.obtenerImages(manejador.getListaArchivos()));
					} catch (GenerarPreviewsException e) {
						e.printStackTrace();
					}
				});
			} catch (BuscadorException e) {
				e.printStackTrace();
				lanzaAlert(AlertType.ERROR, "Error", "No se ha podido cargar la carpeta o archivo: " + rutaArchivo);
			}	
	}

	private void cargarImagenesPreview(List<Image> imagenes) {
		if(imagenes == null || imagenes.isEmpty()) {
			lanzaAlert(AlertType.INFORMATION, "Información", "No se han encontrado imágenes en la carpeta o archivo seleccionado.");
			return;
		}
		if(vboxDerecho.getChildren().size() > 0) {
			vboxDerecho.getChildren().clear();
		}
		for(Image imagen : imagenes) {
			ImageView imageview = new ImageView(imagen);
			imageview.setPreserveRatio(false);
			imageview.fitWidthProperty().bind(scrollPaneDerecho.widthProperty());
			imageview.fitHeightProperty().bind(scrollPaneDerecho.heightProperty());
			vboxDerecho.getChildren().add(imageview);
		}
	}
	
	private void events() {
		switchModo.setOnMouseClicked(event -> {
			if (switchModo.isSelected()) {
				lblSelecciona.setText("Seleccionar carpeta");
				btnSeleccionaArchivo.setText("Seleccionar carpeta");
			} else {
				lblSelecciona.setText("Seleccionar archivo");
				btnSeleccionaArchivo.setText("Seleccionar archivo");
			}
		});
		btnSeleccionaArchivo.setOnMouseClicked(event -> {
			if (switchModo.isSelected()) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("Seleccionar carpeta");
				File carpetaSeleccionada = directoryChooser.showDialog(escena);
				if (carpetaSeleccionada != null)
					rutaArchivo = carpetaSeleccionada.getAbsolutePath();
				cargaPreview();
			} else {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Seleccionar archivo");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg",
						"*.jpeg", "*.gif", "*.jfif", "*.bmp", "*.webp", "*.tiff"));
				File archivoSeleccionado = fileChooser.showOpenDialog(escena);
				if (archivoSeleccionado != null)
					rutaArchivo = archivoSeleccionado.getAbsolutePath();
				cargaPreview();
			}
		});
		btnConvertir.setOnMouseClicked(event -> {
			convertir();
		});
	}

}
