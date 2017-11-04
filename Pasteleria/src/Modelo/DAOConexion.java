package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOConexion {

	private String servidor, usuario, contrasenia, puerto, baseDatos;
	private Connection conexion;


	public DAOConexion(){
		this.servidor = "localhost"; //127.0.0.1
		this.usuario = "postgres";
		this.contrasenia = "12345";
		this.puerto = "5432";
		this.baseDatos = "Pasteleria1Nov";
	}

	//Para conectar a la base de datos
	public boolean conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://" + servidor + ":" + puerto + "/" + baseDatos, usuario, contrasenia);
			return true;
		} catch (Exception ex) {
			//ex.printStackTrace();
			return false;
		}
	}

	///Para cerrar la conexi�n a la base de datos
	public boolean desconectar(){
		try {
			conexion.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//Para recuperar la conexi�n a la base de datos
	public Connection getConexion(){
		return conexion;
	}

}