package Modelo;

import java.sql.Date;

public class DAOVentas {
	int id, clienteid;
	Date fecha;
	double montototal;
	DAOCliente cl;
	public DAOVentas() {
		cl=new DAOCliente();
		clienteid= cl.getIdCliente();
		fecha.toLocalDate();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClienteid() {
		return clienteid;
	}
	public void setClienteid(int clienteid) {
		this.clienteid = clienteid;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getMontototal() {
		return montototal;
	}
	public void setMontototal(double montototal) {
		this.montototal = montototal;
	}
}
