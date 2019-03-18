package Model;

import DAO.PedidosDAO;
import org.apache.log4j.Logger;
import Cache.BumexMemcached;


public class PedidoManager {

	private final BumexMemcached bumexMemcached = BumexMemcached.getInstance();
	private Logger log = Logger.getLogger("Inicio Log");

	
	public void crearPedidos(Pedido nuevoPedido) throws ErrorConexionBDException{
	
		try{
			
			PedidosDAO.insertOrUpdate(nuevoPedido);
		} catch (ErrorConexionBDException e) {
			
			e.errorConexion(e.getMessage());
		}
		
		this.bumexMemcached.set(Integer.toString(nuevoPedido.getIdPedido()), nuevoPedido);
				
	}
	
	
	public void modificarPedidos(Pedido pedidoActualizado) {
		
		try {
			
			/* verifico si el pedido está en el cache
			 * - si existe se actualiza
			 * - si no existe paso directamente a actualizarlo en la bd
			 */		
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(pedidoActualizado.getIdPedido()));
			
			if (pedidoExistente != null)
				this.bumexMemcached.set(Integer.toString(pedidoActualizado.getIdPedido()), pedidoActualizado);
			
			PedidosDAO.insertOrUpdate(pedidoActualizado);
			
		} catch (ErrorConexionBDException e) {
			
			e.errorConexion(e.getMessage());
		}
	}
	
	
	public Pedido buscarPedidosPorId(int idPedido) throws ErrorConexionBDException{
		try {
			
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
			
			if (pedidoExistente == null)
				pedidoExistente = PedidosDAO.select(idPedido);
			
			return pedidoExistente;
			
		} catch (ErrorConexionBDException e) {
			
			e.errorConexion(e.getMessage());
			return null;
			
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
			
			
		} catch (ErrorConexionBDException e) {
			
			e.errorConexion(e.getMessage());
		}
		
		pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
		
		if (pedidoExistente != null)
			this.bumexMemcached.delete(Long.toString(idPedido));

	}
}
