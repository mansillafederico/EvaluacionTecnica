package Model;

import DAO.PedidosDAO;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import Cache.BumexMemcached;


public class PedidoManager {

	private final BumexMemcached bumexMemcached = BumexMemcached.getInstance();
	private Logger log = Logger.getLogger("Inicio Log");

	
	public void crearPedidos(Pedido nuevoPedido) throws ErrorConexionBDException{
	
		try{
			
			PedidosDAO.insertOrUpdate(nuevoPedido);
		} catch (IOException e) {

			log.error(fechaId() + " Error en la conexión con la BD: " + e.getMessage());
			throw new ErrorConexionBDException("Error en la conexión con la BD");
		}
		
		this.bumexMemcached.set(Integer.toString(nuevoPedido.getIdPedido()), nuevoPedido);
				
	}
	
	
	public void modificarPedidos(Pedido pedidoActualizado) throws ErrorConexionBDException {
		
		try {
			
			/* verifico si el pedido está en el cache
			 * - si existe se actualiza
			 * - si no existe paso directamente a actualizarlo en la bd
			 */		
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(pedidoActualizado.getIdPedido()));
			
			if (pedidoExistente != null)
				this.bumexMemcached.set(Integer.toString(pedidoActualizado.getIdPedido()), pedidoActualizado);
			
			PedidosDAO.insertOrUpdate(pedidoActualizado);
			
		} catch (IOException e) {
			
			log.error(fechaId() + " Error en la conexión con la BD: " + e.getMessage());
			throw new ErrorConexionBDException("Error en la conexión con la BD");
		}
	}
	
	
	public Pedido buscarPedidosPorId(int idPedido) throws ErrorConexionBDException{
		try {
			
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
			
			if (pedidoExistente == null)
				pedidoExistente = PedidosDAO.select(idPedido);
			
			return pedidoExistente;
			
		} catch (IOException e) {
			
			log.error(fechaId() + " Error en la conexión con la BD: " + e.getMessage());
			throw new ErrorConexionBDException("Error en la conexión con la BD");
			
		}
		
	}
	

	/* ENUNCIADO: void delete(Pedido pedido): elimina el pedido que corresponde al id recibido.
	 * Recibe un pedido, no un id
	 */
	public void borrarPedidos(int idPedido) throws ErrorConexionBDException{
		
		/*
		 * Busca el pedido en la BD y lo elimina,
		 * Si no falla nada lo busca en el cache y si está lo elimina
		 */
		Pedido pedidoExistente;
		try {
						
			pedidoExistente = PedidosDAO.select(idPedido);
			if (pedidoExistente != null) {
				PedidosDAO.delete(pedidoExistente);
			}
			
			
		} catch (IOException e) {
			
			log.error(fechaId() + " Error en la conexión con la BD: " + e.getMessage());
			throw new ErrorConexionBDException("Error en la conexión con la BD");
		}
		
		pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
		
		if (pedidoExistente != null)
			this.bumexMemcached.delete(Long.toString(idPedido));

	}
	
	
	/*
	 * En esta funcion se prepara un ID de Fecha para el Log
	 */
	private String fechaId() {
		Calendar cal = Calendar.getInstance();
		Date fecha = new Date();
		
		cal.setTime(fecha);
		String año = Integer.toString(cal.get(Calendar.YEAR));
		String mes, dia, hora, min, seg;
		if (cal.get(Calendar.MONTH)<10) 
			mes = "0"+Integer.toString(cal.get(Calendar.MONTH));
		else 
			mes =Integer.toString(cal.get(Calendar.MONTH));
		if (cal.get(Calendar.DAY_OF_MONTH)<10) 
			dia = "0"+Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		else 
			dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if (cal.get(Calendar.HOUR_OF_DAY)<10) 
			hora = "0"+Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		else
			hora = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		if (cal.get(Calendar.MINUTE)<10)
			min = "0" + Integer.toString(cal.get(Calendar.MINUTE));
		else 
			min = Integer.toString(cal.get(Calendar.MINUTE));
		if (cal.get(Calendar.SECOND)<10)
			seg = "0" + Integer.toString(cal.get(Calendar.SECOND));
		else
			seg = Integer.toString(cal.get(Calendar.SECOND));
	    return (año+mes+dia+hora+min+seg);
	}
}
