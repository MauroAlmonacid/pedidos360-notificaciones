package cl.duoc.pedidos360.notificaciones.dto;

public class ItemDTO {

	private Long idProducto;
	private Integer cantidad;
	private Double precioUnitario;

	public ItemDTO() {
	}

	public ItemDTO(Long idProducto, Integer cantidad, Double precioUnitario) {
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	@Override
	public String toString() {
		return "ItemDTO{" +
				"idProducto=" + idProducto +
				", cantidad=" + cantidad +
				", precioUnitario=" + precioUnitario +
				'}';
	}
}
