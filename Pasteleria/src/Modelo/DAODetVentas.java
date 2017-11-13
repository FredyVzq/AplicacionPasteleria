package Modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAODetVentas {
	private int idDet, venta_id,producto_id,cantidad;
	private double precio;
	DAOConexion con;
	DAOProducto prod;
	DAOVentas vent;
	public DAODetVentas(){
		con = new DAOConexion();
		prod = new DAOProducto();
		vent =new DAOVentas();
	}
	public DAODetVentas(int id,int venta, int producto,int cant, double prec) {
		this.idDet=id;
		this.venta_id=venta;
		this.producto_id=producto;
		this.precio=prec;
		venta= vent.getId();
		producto=prod.getIdProducto();
		prec=prod.getPrecio();
	}
	public int getId() {
		return idDet;
	}
	public void setId(int id) {
		this.idDet = id;
	}
	public int getVenta_id() {
		return venta_id;
	}
	public void setVenta_id(int venta_id) {
		this.venta_id = venta_id;
	}
	public int getProducto_id() {
		return producto_id;
	}
	public void setProducto_id(int producto_id) {
		this.producto_id = producto_id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void insertar(DAODetVentas venta) throws SQLException{
		System.out.println("vamos bien");
		String VEN="insert into venta(idventa,fecha,montototal,clienteid) values (default,?,?,?)";
		String DET="insert into detallevta(id, venta_id, producto_id, cantidad, precio) values (default,?,?,?,?)";
		String PRO="update productos set cantidad = ? where id = ?";
		PreparedStatement ven=null,det=null, pro=null; 
		con.getConexion().setAutoCommit(false);
		if(con.conectar()){
			System.out.println("No te emociones solo conecto");
		try {
			ven = con.getConexion().prepareStatement(VEN);
			ven.setDate(1, this.vent.fecha);
			ven.setDouble(2, vent.montototal);
			ven.setInt(3, this.vent.clienteid);
			ven.executeUpdate();
			
			det = con.getConexion().prepareStatement(DET);
			det.setInt(1, this.venta_id);
			det.setInt(2, this.producto_id);
			det.setInt(3, this.cantidad);
			det.setDouble(4, this.precio);
			det.executeUpdate();
			
			pro = con.getConexion().prepareStatement(PRO);
			pro.setInt(1, this.prod.getCantidad()-prod.getCantidadVenta());
			pro.setInt(2, this.prod.getIdProducto());
			pro.executeUpdate();
			
			con.getConexion().commit();
			System.out.println("Ya chingaste");
		} catch (Exception e) {
			con.getConexion().rollback();
			e.printStackTrace();	
			System.out.println("Nel bro");
		}
		finally {
			con.desconectar();
		}
	}else{
		System.out.println("Ni pedo bro");
	}
	}
		
}
