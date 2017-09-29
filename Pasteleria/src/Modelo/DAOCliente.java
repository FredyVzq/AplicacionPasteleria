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
	private String numeroCel;
	private String numeroCasa;
	private boolean estatus;
	private DAOConexion con;
	private PreparedStatement comando;

	public DAOCliente(){
		this.idCliente=0;
		this.nombre="";
		this.apPat="";
		this.apMat="";
		this.ciudad="";
		this.calle="";
		this.numeroCel="";
		this.numeroCasa="";
		this.estatus= true;
		this.con = new DAOConexion();

	}
	public DAOCliente(Integer idCliente, String nombre, String ApePat,String ApeMat, String Ciudad, String Calle, String NumeroCel, String NumeroCasa){ //Constructor que recibe parametros
		this.idCliente=idCliente;
		this.nombre= nombre;
		this.apPat=ApePat;
		this.apMat=ApeMat;
		this.ciudad=Ciudad;
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
            	String sql = "insert into cliente values(default,?,?,?,?,?,?,?,true)";
                comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nombre);
				comando.setString(2, this.apPat);
				comando.setString(3, this.apMat);
				comando.setString(4, this.ciudad);
				comando.setString(5, this.calle);
				comando.setString(6, this.numeroCel);
				comando.setString(7, this.numeroCasa);
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
	            	String sql = "select * from cliente";
	                comando = con.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	cat = new DAOCliente();
	                	cat.nombre = rs.getString("nombre");
	                	cat.apPat = rs.getString("apepat");
	                	cat.apMat = rs.getString("apemat");
	                	cat.ciudad = rs.getString("ciudad");
	                	cat.calle = rs.getString("calle");
	                	cat.numeroCel = rs.getString("numerocel");
	                	cat.numeroCasa = rs.getString("numerocasa");
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
	 				sql="update categoria set nombre=?, apepat=?, apemat=?, ciudad=?, calle=?, numerocel, numerocasa=? where id=?";
	 				comando=con.getConexion().prepareStatement(sql);
	 				comando.setString(1, this.nombre);
					comando.setString(2, this.apPat);
					comando.setString(3, this.apMat);
					comando.setString(4, this.ciudad);
					comando.setString(5, this.calle);
					comando.setString(6, this.numeroCel);
					comando.setString(7, this.numeroCasa);
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
	 				String sql="update productos set estatus = false where id=?";
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

}