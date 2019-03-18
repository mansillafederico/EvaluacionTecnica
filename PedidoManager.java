import DAO.PedidosDAO;
import Model.Pedido;

public class PedidoManager {

	private final BumexMemcached bumexMemcached = BumexMemcached.getInstance();
	
	
	public void crearPedidos(Pedido nuevoPedido) {
	
		try {
			
			/* verifica que el pedido no exista en el cache con este id
			 * si el id no existe, crea el pedido
			 */
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(nuevoPedido.getIdPedido()));
			
			if (pedidoExistente == null){
				
				PedidosDAO.insertOrUpdate(nuevoPedido);
				
			} else {
				
				System.out.println("Pedido existente para ese ID");
				
			}
		} catch (Exception e) {
			
			System.out.println(e.getMessage());			
		}
				
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
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	
	public Pedido buscarPedidosPorId(int idPedido) {
		try {
			
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
			
			if (pedidoExistente == null)
				pedidoExistente = PedidosDAO.select(idPedido);
			
			return pedidoExistente;
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return null;
			
		}
		
	}
	

	/* ENUNCIADO: void delete(Pedido pedido): elimina el pedido que corresponde al id recibido.
	 * Recibe un pedido, no un id
	 */
	public boolean borrarPedidos(int idPedido) {
		
		try {
			
			/* verifico si el pedido está en el cache
			 * - si existe lo elimina
			 * - si no existe paso directamente a borrarlo en la bd
			 */		
			Pedido pedidoExistente = (Pedido) this.bumexMemcached.get(Integer.toString(idPedido));
			
			if (pedidoExistente != null)
				this.bumexMemcached.delete(Long.toString(idPedido));
			
			pedidoExistente = PedidosDAO.select(idPedido);
			if (pedidoExistente != null) {
				PedidosDAO.delete(pedidoExistente);
				return true;
			}
			
			return false;
			
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return false;
		}
	}
}
