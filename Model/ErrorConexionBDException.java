package Model;

import org.apache.log4j.Logger;
import java.util.Date;

public class ErrorConexionBDException extends Exception{

	public static final long serialVersionUID = 700L;
	private Logger log;
	private Date fecha;
	
	
	public ErrorConexionBDException () {
		
	}
	
	public void errorConexion(String mensaje) {
		log.error(Integer.toString(this.fecha.getDate()) + " " +
				Long.toString(this.fecha.getTime()) + " " +
				"Error en la conexión con la BD: " + mensaje);
	}
}
