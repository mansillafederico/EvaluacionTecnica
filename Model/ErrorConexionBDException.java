package Model;

import org.apache.log4j.Logger;
import java.util.Date;

public class ErrorConexionBDException extends Exception{

	public static final long serialVersionUID = 700L;
	private Logger log;
	
	
	public ErrorConexionBDException (String mensaje) {
		log.error(mensaje);
	}

}
