package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOBases {
	private Integer idBases;
	private String nombre;
	private String Precio;
	private String Existencia;
	private boolean estatus;
	private DAOConexion con;
	private PreparedStatement comando;

	public DAOBases(){
		this.idBases=0;
		this.nombre="";
		this.Precio="";
		this.Existencia="";
		this.estatus= true;
		this.con = new DAOConexion();

	}
	public DAOBases(Integer idBases, String nombre, String precio, String existencia, boolean estatus){ //Constructor que recibe parametros
		this.idBases=idBases;
		this.nombre= nombre;
		this.Precio= precio;
		this.Existencia= existencia;
		this.estatus= estatus;
	}

	//Metodos Gey y Set
		public int getIdBases() {
	        return idBases;
	    }
		public void setIdBases(int Basesid) {
	        this.idBases = Basesid;
	    }
		public String getNombre() {
	        return nombre;
	    }
		public void setNombre(String nomBase) {
	        this.nombre = nomBase;
	    }
		public String getPrecio() {
	        return Precio;
	    }
		public void setPrecio(String precioBase) {
	        this.Precio =precioBase;
	    }
		public String getExistencia() {
	        return Existencia;
	    }
		public void Existencia(String Existecia) {
	        this.Existencia =Existecia;
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
	            	String sql = "insert into cliente values(default,?,?,?,true)";
	                comando = con.getConexion().prepareStatement(sql);
					comando.setString(1, this.nombre);
					comando.setString(2, this.Precio);
					comando.setString(3, this.Existencia);
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
		            	String sql = "select nombre, precio, existencia from bases";
		                comando = con.getConexion().prepareStatement(sql);
		                rs = comando.executeQuery();
		                while(rs.next()){
		                	cat = new DAOBases();
		                	cat.nombre = rs.getString("nombre");
		                	cat.Precio = rs.getString("precio");
		                	cat.Existencia = rs.getString("existencia");
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
		 				sql="update bases set nombre=?, precio=?, Existecia=? where id=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setString(1, this.nombre);
						comando.setString(2, this.Precio);
						comando.setString(3, this.Existencia);
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

		 public boolean eliminar(){
				try {
		 			if(con.conectar()){
		 				String sql="update bases set estatus = false where id=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setInt(1, this.idBases);
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
		public void setExistencia(String text) {
			// TODO Auto-generated method stub

		}

	}