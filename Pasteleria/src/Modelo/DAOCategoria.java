package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCategoria {
	private PreparedStatement comando;
	private Integer id;
	private String nombre;
	private DAOConexion con;

	public DAOCategoria(){
		this.id=0;
		this.nombre= "";
	    this.con = new DAOConexion();
	}

	public DAOCategoria(Integer id, String nombre){
		this.id=id;
		this.nombre= nombre;
	}


	public int getIdCategoria(){
		return id;
	}
	public void setIdCategoria(Integer id){
		this.id=id;
	}

	/* NOMBRE */
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String string){
		this.nombre=string;
	}

	//Metodos
	 public int consultarIdCategoria(String consulta){
		 ResultSet rs=null;
		 int idCat=0;
		 try{
			 if(con.conectar()){
				 comando=con.getConexion().prepareStatement(consulta);
				 rs=comando.executeQuery();
				 while(rs.next()){
					 idCat=rs.getInt("id");
				 }
			 }
		 }
		 catch(Exception e){

		 }
		 finally {
			con.desconectar();
			}
		 return idCat;

	 }
	 public Boolean insertar(){
	        Boolean bandera = false;

	        try{
	            if(con.conectar()) {
	            	String sql = "insert into categoria values(default,?,TRUE)";
	                comando = con.getConexion().prepareStatement(sql);
	                comando.setString(1, this.nombre);
	                bandera = comando.execute();

	                bandera =true;
	            }
	        }
	        catch (Exception ex){
	            ex.printStackTrace();
	            bandera=false;
	        }
	        finally {
	            con.desconectar();
	        }
	        return bandera;
	    }

	 public ObservableList<DAOCategoria>mostrar(){
		 ObservableList<DAOCategoria> lista=FXCollections.observableArrayList();
	        DAOCategoria cat = null;
	        ResultSet rs = null;
	        try{
	            if(con.conectar()) {
	            	String sql = "select * from categoria where estatus='TRUE'";
	                comando = con.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	cat = new DAOCategoria();
	                	cat.nombre = rs.getString("nombre");
	                	cat.id = rs.getInt("id");
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
	 public boolean editar(){
			String sql="";
			try {
	 			if(con.conectar()){
	 				sql="update categoria set nombre=? where id=?";
	 				comando=con.getConexion().prepareStatement(sql);
	 				comando.setString(1, this.nombre);
	 				comando.setInt(2, this.id);
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
	 public List<Object> getListaMarcas(){
			List<Object> lista = new ArrayList<Object>();
			try{
				if(con.conectar()){
					String listaA="select nombre from categoria ";
					PreparedStatement comando=con.getConexion().
					prepareStatement(listaA);
					comando.execute();
					ResultSet tablaTemporal=comando.executeQuery();
					while(tablaTemporal.next()){
						DAOCategoria nombres = new DAOCategoria();
						nombres.setNombre(tablaTemporal.getString("nombre"));
						lista.add(nombres);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return lista;
		}
	 public String toString(){
		 return this.getNombre();
	 }
	 public boolean eliminar(){
			try {
	 			if(con.conectar()){
	 				String sql="update categoria set estatus = false where id=?";
	 				comando=con.getConexion().prepareStatement(sql);
	 				comando.setInt(1, this.id);
	 				comando.executeUpdate();
	 				System.out.println("Eliminado");
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
