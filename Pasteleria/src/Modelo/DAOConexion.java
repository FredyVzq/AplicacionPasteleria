package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOConexion {

	private String servidor, usuario, contrasenia, puerto, baseDatos;
	private Connection conexion;


	public DAOConexion(){
		this.servidor = "localhost"; //127.0.0.1
		this.usuario = "postgres";
		this.contrasenia = "qwerty123";
		this.puerto = "5432";
		this.baseDatos = "PasteleriaDB2.0";
	}

	//Para conectar a la base de datos
	public boolean conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://" + servidor + ":" + puerto + "/" + baseDatos, usuario, contrasenia);
			System.out.println("Conectado a: " + conexion.getCatalog());
			return true;
		} catch (Exception ex) {
			//ex.printStackTrace();
			return false;
		}
	}

	///Para cerrar la conexión a la base de datos
	public boolean desconectar(){
		try {
			conexion.close();
			System.out.println("Conexión cerrada");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//Para recuperar la conexión a la base de datos
	public Connection getConexion(){
		return conexion;
	}

}