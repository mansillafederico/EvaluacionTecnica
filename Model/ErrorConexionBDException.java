package Model;

import java.io.IOException;

import org.apache.log4j.Logger;

public class ErrorConexionBDException extends IOException{

	public static final long serialVersionUID = 700L;
	private Logger log;
	
	
	public ErrorConexionBDException (String mensaje) {
		log.error(mensaje);
	}

}
