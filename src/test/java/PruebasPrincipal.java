import javax.swing.JFrame;

import rhn.convertir.Convertidor;

public class PruebasPrincipal {

	
	
	public static void main (String[] args) {
		Convertidor co = new Convertidor();
		co.escogerPorLotes(new JFrame());
		//co.escogerCarpeta(new JFrame());
		//co.escogerImagen(new JFrame());
		co.escogerDestino(new JFrame());
		co.convertirImagen("jpg");
	}
	
	
	
}
