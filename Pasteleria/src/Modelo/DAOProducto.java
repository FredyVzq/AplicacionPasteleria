package Modelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOProducto {
    	private PreparedStatement comando;
    	private int idcategoria;
		private int id_producto;
		private int idmarca;
		String nombre;
		private String codigo;
		private String tipo;
		private String nombreCategoria;
		private String precio;
		private DAOConexion con;
		private ObservableList<DAOProducto> lista;

		public DAOProducto(){
			this.id_producto=0;
			this.idcategoria=0;
			this.nombreCategoria="";
			this.nombre= "";
			this.codigo= "";
			this.tipo= "";
			this.precio= "";
		    this.con = new DAOConexion();
		    this.idmarca=0;//Aqui se encuentra el error
		}

		public DAOProducto(int id_producto, String nombre, String codigo, String tipo,
				String precio,int idcategoria,String nombrecategoria, int idmarca){
			this.id_producto=id_producto;
			this.nombre= nombre;
			this.codigo= codigo;
			this.tipo= tipo;
			this.precio= precio;
			this.idcategoria=idcategoria;
			this.nombreCategoria=nombrecategoria;
			this.idmarca=idmarca;
		}

		//---[Getters and Setters]--->

		/*  ID */
		public int getIdProducto(){
			return id_producto;
		}
		public void setIdProducto(Integer id_producto){
			this.id_producto=id_producto;
		}

		/* NOMBRE */
		public String getNombre(){
			return nombre;
		}
		public void setNombre(String nombre){
			this.nombre=nombre;
		}

		/* codigo */
		public String geCodigo(){
			return codigo;
		}
		public void setCodigo(String codigo){
			this.codigo=codigo;
		}

		/* tipo */
		public String getTipo(){
			return tipo;
		}
		public void setTipo(String tipo){
			this.tipo=tipo;
		}

		/* precio */
		public String getPrecio(){
			return precio;
		}
		public void setPrecio(String precio){
			this.precio=precio;
		}
		public String getNombreCategoria(){
			return nombreCategoria;
		}
		public void setNombreCategoria(String nombreCategoria){
			this.nombreCategoria=nombreCategoria;
		}
		public int getIdCategoria(){
			return idcategoria;
		}
		public void setIdCategoria(int idcategoria){
			this.idcategoria=idcategoria;
		}
		public int getIdMarca(){
			return idmarca;
		}
		public void setIdMarca(int idmarca){
			this.idmarca=idmarca;
		}
		//a ver ejecutalo? okk
		 public Boolean insertar(){
		        Boolean bandera = false;

		        try{
		            if(con.conectar()) {
		            	String sql = "insert into productos values(default,?,?,?,?,true,?,?)";
		                comando = con.getConexion().prepareStatement(sql);
		                comando.setString(1, this.nombre);
		                comando.setString(2, this.codigo);
		                comando.setString(3, this.tipo);
		                comando.setString(4, this.precio);
		                comando.setInt(5, this.idmarca);
		                comando.setInt(6,this.idcategoria );

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
		 public ObservableList<DAOProducto>mostrar(){
			 ObservableList<DAOProducto> lista=FXCollections.observableArrayList();
		        DAOProducto product = null;
		        ResultSet rs = null;
		        try{
		            if(con.conectar()) {
		            	String sql = "select * from productos where estatus='TRUE'";
		                comando = con.getConexion().prepareStatement(sql);
		                rs = comando.executeQuery();
		                while(rs.next()){
		                	product = new DAOProducto();
		                	product.nombre = rs.getString("nombre");
		                	product.codigo = rs.getString("codigo");
		                	product.tipo = rs.getString("tipo");
		                	product.precio=rs.getString("precio");
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

		 public boolean editar(){
				String sql="";
				try {
		 			if(con.conectar()){
		 				sql="update productos set nombre=?, codigo=?, tipo=?, precio=? where id=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setString(1, this.nombre);
		 				comando.setString(2, this.codigo);
		 				comando.setString(3, this.tipo);
		 				comando.setString(4, this.precio);
		 				comando.setInt(5, this.id_producto);
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
		 public ObservableList<DAOProducto> consultar(String consulta){
		   		ResultSet rs = null;
		   		try {
		   			if(con.conectar()){
		   				comando = con.getConexion().prepareStatement(consulta);
		   	  			rs =  comando.executeQuery();
		   	  			while(rs.next()){
		   	  				DAOProducto l = new DAOProducto();
		   	  			    l.setIdProducto(rs.getInt("id"));
		   	  				l.setNombre(rs.getString("nombre"));
		   	  				l.setCodigo(rs.getString("codigo"));
		   	  				l.setPrecio(rs.getString("precio"));
		   	  				l.setTipo(rs.getString("tipo"));
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
		 				String sql="update productos set estatus = false where id=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setInt(1, this.id_producto);
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
