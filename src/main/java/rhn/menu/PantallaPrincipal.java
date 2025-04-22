package rhn.menu;

import rhn.menuplantilla.JFramePadre;
import javax.swing.JPanel;


public class PantallaPrincipal extends JFramePadre{
	
	private JPanel panel = new PanelPrincipal();

	public PantallaPrincipal() {
		super();
		add(panel);
	}
	
	public void mostrarPantalla() {
		setVisible(true);
	}
	
	
	
	
	
}
