package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCliente {
	private Integer idCliente;
	private String nombre;
	private String apPat;
	private String apMat;
	private String ciudad;
	private String calle;
	private String colonia;
	private String numeroCel;
	private String numeroCasa;
	private boolean estatus;
	private DAOConexion con;
	private PreparedStatement comando;
	private ObservableList<DAOCliente> lista;

	public DAOCliente(){
		this.idCliente=0;
		this.nombre="";
		this.apPat="";
		this.apMat="";
		this.ciudad="";
		this.colonia="";
		this.calle="";
		this.numeroCel="";
		this.numeroCasa="";
		this.estatus= true;
		this.con = new DAOConexion();

	}
	public DAOCliente(Integer idCliente, String nombre, String ApePat,String ApeMat, String Ciudad,String Colonia, String Calle, String NumeroCel, String NumeroCasa){ //Constructor que recibe parametros
		this.idCliente=idCliente;
		this.nombre= nombre;
		this.apPat=ApePat;
		this.apMat=ApeMat;
		this.ciudad=Ciudad;
		this.colonia=Colonia;
		this.calle=Calle;
		this.numeroCel=NumeroCel;
		this.numeroCasa=NumeroCasa;


	}
	public int getIdCliente() {
        return idCliente;
    }
	public void setIdCliente(Integer clienteid) {
        this.idCliente = clienteid;
    }
	public String getNombre() {
        return nombre;
    }
	public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	public String getApPat() {
        return apPat;
    }
	public void setApPat(String apPat) {
        this.apPat = apPat;
    }
	public String getApMat() {
        return apMat;
    }
	public void setApMat(String apMat) {
        this.apMat = apMat;
    }
	public String getCiudad() {
        return ciudad;
    }
	public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
	public String getColonia() {
        return colonia;
    }
	public void setColonia(String colonia) {
        this.colonia = colonia;
    }
	public String getCalle() {
        return calle;
    }
	public void setCalle(String calle) {
        this.calle=calle;
    }
	public String getNumeroCel() {
        return numeroCel;
    }
	public void setNumeroCel(String numeroCel) {
        this.numeroCel =numeroCel;
    }
	public String getNumeroCasa() {
        return numeroCasa;
    }
	public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa =numeroCasa;
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
            	String sql = "insert into cliente values(default,?,?,?,?,?,?,?,?,true)";
                comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.apPat);
				comando.setString(3, this.apMat);
				comando.setString(4, this.ciudad);
				comando.setString(5, this.colonia);
				comando.setString(6, this.calle);
				comando.setString(7, this.numeroCel);
				comando.setString(8, this.numeroCasa);
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
	public ObservableList<DAOCliente>mostrar(){
		 ObservableList<DAOCliente> lista=FXCollections.observableArrayList();
	        DAOCliente cat = null;
	        ResultSet rs = null;
	        try{
	            if(con.conectar()) {
	            	String sql = "select * from cliente where estatus='TRUE' and idcliente>1" ;
	                comando = con.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	cat = new DAOCliente();
	                	cat.nombre = rs.getString("nombre");
	                	cat.apPat = rs.getString("apepat");
	                	cat.apMat = rs.getString("apemat");
	                	cat.ciudad = rs.getString("ciudad");
	                	cat.colonia=rs.getString("colonia");
	                	cat.calle = rs.getString("calle");
	                	cat.numeroCel = rs.getString("numerocel");
	                	cat.numeroCasa = rs.getString("numerocasa");
	                	cat.idCliente=rs.getInt("idcliente");
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
	public ObservableList<DAOCliente>nombreCliente(){
		 ObservableList<DAOCliente> lista=FXCollections.observableArrayList();
	        DAOCliente cat = null;
	        ResultSet rs = null;
	        try{
	            if(con.conectar()) {
	            	String sql = "select nombre from cliente";
	                comando = con.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	cat = new DAOCliente();
	                	cat.nombre = rs.getString("nombre");
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
	public String toString(){
		 return this.getNombre()+" "+this.getApPat()+" "+this.getApMat();
	 }
	//---------------------------------------------------
	 public boolean editar(){
			String sql="";
			try {
	 			if(con.conectar()){
	 				sql="update cliente set nombre=?, apepat=?, apemat=?, ciudad=?,colonia=?, calle=?, numerocel=?, numerocasa=? where idcliente=?";
	 				comando=con.getConexion().prepareStatement(sql);
	 				comando.setString(1, this.nombre);
					comando.setString(2, this.apPat);
					comando.setString(3, this.apMat);
					comando.setString(4, this.ciudad);
					comando.setString(5, this.colonia);
					comando.setString(6, this.calle);
					comando.setString(7, this.numeroCel);
					comando.setString(8, this.numeroCasa);
					comando.setInt(9, this.idCliente);
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
	 				String sql="update cliente set estatus =false where idcliente=?";
	 				comando=con.getConexion().prepareStatement(sql);
	 				comando.setInt(1, this.idCliente);
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
	 public ObservableList<DAOCliente> consultar(String consulta){
	   		ResultSet rs = null;
	   		try {
	   			if(con.conectar()){
	   				comando = con.getConexion().prepareStatement(consulta);
	   	  			rs =  comando.executeQuery();
	   	  			while(rs.next()){
	   	  				DAOCliente l = new DAOCliente();
	   	  			    l.setIdCliente(rs.getInt("idcliente"));
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
}