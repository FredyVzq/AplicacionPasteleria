package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOMarcas {
	private PreparedStatement comando;
	private int id;
	private String nombre, proveedor;
	private DAOConexion cone;

	public DAOMarcas(){
		this.id=0;
		this.nombre="";
		this.proveedor="";
		this.cone = new DAOConexion();
	}
	public DAOMarcas(Integer id, String nombre, String proveedor){
		this.id=id;
		this.nombre= nombre;
		this.proveedor=proveedor;
	}
	public int getId(){
		return id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public String getNombre(){
		return nombre;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public String getProveedor(){
		return proveedor;
	}
	public void setProveedor(String proveedor){
		this.proveedor=proveedor;
	}

	 public int consultarIdMarca(String consulta){
		 ResultSet rs=null;
		 int idCat=0;
		 try{
			 if(cone.conectar()){
				 comando=cone.getConexion().prepareStatement(consulta);
				 rs=comando.executeQuery();
				 while(rs.next()){
					 idCat=rs.getInt("id");
				 }
			 }
		 }
		 catch(Exception e){

		 }
		 finally {
			cone.desconectar();
			}
		 return idCat;
		 //Wtf? Pense que no lo tenia :3......okkkkkkkkkkkkkkk...ejecutalo :X
	 }

	public Boolean insertar(){//?? que quieres ver? o hacer? esperaaaaaaa  oks<_<
        Boolean bandera = false;

        try{
            if(cone.conectar()) {
            	String sql = "insert into marca values(default,?,?)";
                comando = cone.getConexion().prepareStatement(sql);
                comando.setString(1, nombre);
                comando.setString(2, proveedor);
                bandera = comando.execute();
                bandera =true;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            bandera=false;
        }
        finally {
            cone.desconectar();
        }
        return bandera;
    }
	 public ObservableList<DAOMarcas>mostrar(){
		 ObservableList<DAOMarcas> lista=FXCollections.observableArrayList();
	        DAOMarcas product = null;
	        ResultSet rs = null;
	        try{
	            if(cone.conectar()) {
	            	String sql = "select * from marca";
	                comando = cone.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	product = new DAOMarcas();
	                	product.nombre = rs.getString("nombremarca");
	                	product.proveedor = rs.getString("proveedormarca");
	                	lista.add(product);
	                }
	            }
	        }
	        catch (Exception ex){
	            ex.printStackTrace();
	        }
	        finally {
	            cone.desconectar();
	        }
	        return lista;
	    }
	 public String toString(){
		 return this.getNombre();
	 }
}
