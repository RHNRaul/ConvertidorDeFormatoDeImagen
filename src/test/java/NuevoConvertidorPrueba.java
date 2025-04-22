import rhn.exceptions.BuscadorException;
import rhn.exceptions.ConvertidorException;
import rhn.exceptions.SalidaException;
import rhn.manejador.Manejador;

public class NuevoConvertidorPrueba {
	public static void main (String args[]) {
		
		Manejador manejador = new Manejador();
		try {
			//manejador.convertirArchivo("C:\\Users\\PC\\Downloads\\Emperatriz.jfif", "jpg");
			manejador.convertirArchivos("C:\\Users\\PC\\Documents\\Videos\\Espada", "jpg");
		} catch (BuscadorException | ConvertidorException | SalidaException e) {
			e.printStackTrace();
		}
	}
}
