package Model;

public class Pedido {

	private int idPedido;
	private String nombre;
	private double monto;
	private float descuento;
	
	public Pedido(int idPedido, String nombre, double monto, float descuento) {
		super();
		this.setIdPedido(idPedido);
		this.setNombre(nombre);
		this.setMonto(monto);
		this.setDescuento(descuento);
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	
	
}
