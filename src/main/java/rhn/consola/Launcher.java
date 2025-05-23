package rhn.consola;

import javafx.application.Application;
import javafx.stage.Stage;
import rhn.controlador.PantallaFXMLController;

public class Launcher extends Application {
	@Override
	public void start(Stage escenario) throws Exception {	
		PantallaFXMLController.lanzaPantallaPrincipal(escenario);	
	}	
	public static void main(String [] args) {
		launch(args);
	}
}
