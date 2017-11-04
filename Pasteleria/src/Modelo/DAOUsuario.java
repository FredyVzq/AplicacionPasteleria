package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOUsuario { //por cada tabla se crea clase dao, es la que va a la BD
    private int idUsuario;
    private String nomUsuario, contrasenia, nivel, usuarioManejador;
    private boolean estatus;
    private DAOConexion con;
    private PreparedStatement comando;
    private ObservableList<DAOUsuario> lista;

    public DAOUsuario(){
        this.idUsuario = 0;
        this.nomUsuario = "";
        this.contrasenia = "";
        this.nivel = "";
        this.usuarioManejador="";
        this.con = new DAOConexion();
        this.lista = FXCollections.observableArrayList();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNivel() {
        return nivel;
    }
    public String getUsuarioManejador(){
    	return usuarioManejador;
    }
    public boolean getEstatus() {
        return estatus;
    }

    public void setIdUsuario(int usuarioid) {
        this.idUsuario = usuarioid;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    public void setUsuarioManejador(String usuarioManejador){
    	this.usuarioManejador= usuarioManejador;
    }
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }


    public DAOUsuario validarDatos(){
        DAOUsuario usuario = null;
        ResultSet rs = null;
        try{
            if(con.conectar()) {
            	String sql = "select * from usuarios where nomusuario = '" + this.nomUsuario + "' and contrasenia = '" + this.contrasenia + "'";
                comando = con.getConexion().prepareStatement(sql);
                rs = comando.executeQuery();
                while(rs.next()){
                    usuario = new DAOUsuario();
                    usuario.nomUsuario = rs.getString("nomusuario");
                    usuario.nivel = rs.getString("estatus");
                    usuario.idUsuario = rs.getInt("idusuario");
                }

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            con.desconectar();
        }
        return usuario;
    }

    public boolean insertar(){ //Metodos de DAO
		try {
			String sql = "";
			if(con.conectar()){//validar si se establecio la conexion
				sql = "insert into usuarios values (default,?,?,?,true,'admin')";
				comando = con.getConexion().prepareStatement(sql); //Esta es toda la ruta de ejecucion de la consulta
				comando.setString(1, this.nomUsuario);
				comando.setString(2, this.contrasenia);
				comando.setString(3, this.nivel);
				comando.execute();
				return true;
			}
			else{
				return false;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
    public ObservableList<DAOUsuario> obtenerNivel(){
    	ObservableList<DAOUsuario> lista=FXCollections.observableArrayList();
    	ResultSet rs =null;
    	try {
    		if (con.conectar()) {
				String sql="Select nivel from usuarios where idusuario="+getIdUsuario()+";";
				comando = con.getConexion().prepareStatement(sql);
				rs=comando.executeQuery();
				while (rs.next()) {
					DAOUsuario user = new DAOUsuario();
					user.setNivel(rs.getString("nivel"));
					lista.add(user);
				}
			}
		} catch (Exception e) {
		}finally {
			con.desconectar();
		}
		return lista;
    }
    public String toString(){
		 return this.getNivel();
	 }
    public boolean eliminar(){
		try {
			if(con.conectar()){
				String sql="update usuarios set estatus = false where idusuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1, this.idUsuario);
				comando.execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}

    public boolean editar(){
		String sql = "";
		try {
			if(con.conectar()){
				sql="update usuarios set nomusuario = ?, contrasenia = ?, nivel = ? where idusuario = ?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setString(1, this.nomUsuario);
				comando.setString(2, this.contrasenia);
				comando.setString(3, this.nivel);
				comando.setInt(4, this.idUsuario);//El ID del usuario a modificar
				comando.execute();
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			con.desconectar();
		}
	}
    public ObservableList<DAOUsuario>mostrar(){
		 ObservableList<DAOUsuario> lista=FXCollections.observableArrayList();
	        DAOUsuario product = null;
	        ResultSet rs = null;
	        try{
	            if(con.conectar()) {
	            	String sql = "select * from usuarios where estatus='TRUE'";
	                comando = con.getConexion().prepareStatement(sql);
	                rs = comando.executeQuery();
	                while(rs.next()){
	                	product = new DAOUsuario();
	                	product.nomUsuario = rs.getString("nomusuario");
	                	product.contrasenia = rs.getString("contrasenia");
	                	product.nivel = rs.getString("nivel");
	                	lista.add(product);
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

  	public boolean reactivar(){
		try{
			if(con.conectar()){
				String sql = "update usuarios set estatus=true where idUsuario=?";
				comando = con.getConexion().prepareStatement(sql);
				comando.setInt(1,this.idUsuario);
				comando.execute();
			}
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			con.desconectar();
		}
	}

}