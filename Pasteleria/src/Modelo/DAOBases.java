package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOBases {
	private int idbases;
	private String nombre;
	private String precio;
	private String existencia;
	private boolean estatus;
	private DAOConexion con;
	private PreparedStatement comando;
	private ObservableList<DAOBases> lista;


	public DAOBases(){
		this.idbases=0;
		this.nombre="";
		this.precio="";
		this.existencia="";
		this.estatus= true;
		this.con = new DAOConexion();

	}
	public DAOBases(int idbases, String nombre, String precio, String existencia, boolean estatus){ //Constructor que recibe parametros
		this.idbases=idbases;
		this.nombre= nombre;
		this.precio= precio;
		this.existencia= existencia;
		this.estatus= estatus;
	}

	//Metodos Gey y Set
		public int getIdbases() {
	        return idbases;
	    }
		public void setIdbases(int idbases) {
	        this.idbases = idbases;
	    }
		public String getNombre() {
	        return nombre;
	    }
		public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }
		public String getPrecio() {
	        return precio;
	    }
		public void setPrecio(String precio) {
	        this.precio =precio;
	    }
		public String getExistencia() {
	        return existencia;
	    }
		public void setExistencia(String existecia) {
	        this.existencia =existecia;
	    }
		public boolean getEstatus(){
			return estatus;
		}
		public void setEstatus(boolean estatus){
			this.estatus=estatus;
		}

		public Boolean insertar(){
	        Boolean result = false;

	        try{
	            if(con.conectar()) {
	            	String sql = "insert into bases values(default,?,?,?,true)";
	                comando = con.getConexion().prepareStatement(sql);
					comando.setString(1, this.nombre);
					comando.setString(2, this.precio);
					comando.setString(3, this.existencia);
					result = comando.execute();

					 result=true;
	            }
	        }
	        catch (Exception ex){
	            ex.printStackTrace();
	            result=false;
	        }
	        finally {
	            con.desconectar();
	        }
	        return result;
	    }
		//----------------------------------
		public ObservableList<DAOBases>mostrar(){
			 ObservableList<DAOBases> lista=FXCollections.observableArrayList();
		        DAOBases cat = null;
		        ResultSet rs = null;
		        try{
		            if(con.conectar()) {
		            	String sql = "select * from bases where estatus='TRUE'";
		                comando = con.getConexion().prepareStatement(sql);
		                rs = comando.executeQuery();
		                while(rs.next()){
		                	cat = new DAOBases();
		                	cat.nombre = rs.getString("nombre");
		                	cat.precio = rs.getString("precio");
		                	cat.existencia = rs.getString("existencia");
		                	cat.idbases = rs.getInt("idbases");
		                	lista.add(cat);
		                }
		            }
		        }
		        catch (Exception ex){
		            ex.printStackTrace();
		        }
		        finally {
		            con.desconectar();
		        }
		        return lista;
		    }

		//---------------------------------------------------
		 public boolean editar(){
				String sql="";
				try {
		 			if(con.conectar()){
		 				sql="update bases set nombre=?, precio=?, existencia=? where idbases=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setString(1, this.nombre);
		 				comando.setString(2, this.precio);
		 				comando.setString(3, this.existencia);
		 				comando.setInt(4, this.idbases);
		 				comando.execute();
		 				return true;
		 			}
		 			else{
		 				return false;

		 			}
		 		} catch (Exception e) {
		 			return false;
		 		}
		 		finally{
		 			con.desconectar();
		 		}
			}

		 public ObservableList<DAOBases> consultar(String consulta){
		   		ResultSet rs = null;
		   		try {
		   			if(con.conectar()){
		   				comando = con.getConexion().prepareStatement(consulta);
		   	  			rs =  comando.executeQuery();
		   	  			while(rs.next()){
		   	  				DAOBases l = new DAOBases();
		   	  			    l.setIdbases(rs.getInt("idbases"));
		   	  				l.setNombre(rs.getString("nombre"));
		   	  				l.setPrecio(rs.getString("precio"));
		   	  				l.setExistencia(rs.getString("existencia"));
		   	  				lista.add(l);
		   	  			}
		   			}
		   		} catch (Exception ex) {
		   		}
		   		finally{
		   			con.desconectar();
		   		}
		 		return lista;
		   	}

		 public boolean eliminar(){
				try {
		 			if(con.conectar()){
		 				String sql="update bases set estatus =false where idbases=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setInt(1, this.idbases);
		 				comando.executeUpdate();
		 			}
		 			return true;

		 		} catch (Exception e) {
		 			return false;
		 		}
		 		finally{
		 			con.desconectar();
		 		}
			}

}
