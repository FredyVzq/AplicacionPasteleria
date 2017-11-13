
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
		private int cantidad;
		private int cantidadVenta;
		String nombre;
		private String codigo;
		private String tipo;
		private String nombreCategoria;
		private double precio;
		private DAOConexion con;
		private boolean existe;
		private ObservableList<DAOProducto> lista;

		public DAOProducto(){
			this.id_producto=0;
			this.idcategoria=0;
			this.nombreCategoria="";
			this.nombre= "";
			this.codigo= "";
			this.tipo= "";
			this.precio= 0.0;
		    this.con = new DAOConexion();
		    this.idmarca=0;//Aqui se encuentra el error
		    this.cantidad=0;
		    this.cantidadVenta=0;
		}


		public DAOProducto(int id_producto, String nombre, String codigo, String tipo,
				double precio,int idcategoria,String nombrecategoria, int idmarca){
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
		public String getCodigo(){
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
		public double getPrecio(){
			return precio;
		}
		public void setPrecio(double precio){
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
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public int getCantidadVenta() {
			return cantidadVenta;
		}
		public void setCantidadVenta(int cantidadVenta) {
			this.cantidadVenta = cantidadVenta;
		}

		//a ver ejecutalo? okk
		 public Boolean insertar(){
		        Boolean bandera = false;

		        try{
		            if(con.conectar()) {
		            	String sql = "insert into productos values(default,?,?,?,?,true,?,?,?)";
		                comando = con.getConexion().prepareStatement(sql);
		                comando.setString(1, this.nombre);
		                comando.setString(2, this.codigo);
		                comando.setString(3, this.tipo);
		                comando.setDouble(4, this.precio);
		                comando.setInt(5, this.cantidad);
		                comando.setInt(6, this.idmarca);
		                comando.setInt(7,this.idcategoria );

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
		                	product.precio=rs.getDouble("precio");
		                	product.cantidad=rs.getInt("cantidad");
		                	product.id_producto=rs.getInt("id");
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
		 				comando.setDouble(4, this.precio);
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
		 public String toString(){
			 return this.getNombre();
		 }
		 public ObservableList<DAOProducto> consultar(String codigo){
			 existe=false;
		   		ResultSet rs = null;
		   		try {
		   			if(con.conectar()){
		   				comando = con.getConexion().prepareStatement("select * from productos where codigo= '"+codigo+"' and cantidad>1");
		   	  			rs =  comando.executeQuery();
		   	  			while(rs.next()){
		   	  				this.id_producto=rs.getInt("id");
		   	  				this.nombre=rs.getString("nombre");
		   	  				this.codigo=rs.getString("codigo");
		   	  				this.precio=rs.getDouble("precio");
		   	  				this.tipo=rs.getString("tipo");
		   	  				this.cantidad=rs.getInt("cantidad");
		   	  				existe=true;
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
		 /*----------------------------------------------------------------------
		  * -----------------------------------------------------------------------
		  * -------------------------------------------------------------------------
		  * ---------------------------------------------------------------------------
		  * ----------------------------------------------------------------------------*/
		 public ObservableList<DAOProducto>mostrarInventario(){
			 ObservableList<DAOProducto> lista=FXCollections.observableArrayList();
		        DAOProducto product = null;
		        ResultSet rs = null;
		        try{
		            if(con.conectar()) {
		            	String sql = "select * from productos where estatus='TRUE' and cantidad>0";
		                comando = con.getConexion().prepareStatement(sql);
		                rs = comando.executeQuery();
		                while(rs.next()){
		                	product = new DAOProducto();
		                	product.nombre = rs.getString("nombre");
		                	product.codigo = rs.getString("codigo");
		                	product.tipo = rs.getString("tipo");
		                	product.precio=rs.getDouble("precio");
		                	product.cantidad=rs.getInt("cantidad");
		                	product.id_producto=rs.getInt("id");
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
		 public Boolean insertarInventario(){
		        Boolean bandera = false;

		        try{
		            if(con.conectar()) {
		            	String sql = "update productos set cantidad=? where nombre=?";
		                comando = con.getConexion().prepareStatement(sql);
		                comando.setInt(1,this.cantidad);
		                comando.setString(2,this.nombre);
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

		 public boolean editarStock(){
				String sql="";
				try {
		 			if(con.conectar()){
		 				sql="update productos set cantidad=? where id=?";
		 				comando=con.getConexion().prepareStatement(sql);
		 				comando.setInt(1, this.cantidad);
		 				comando.setInt(2, this.id_producto);
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


		public boolean getExiste() {
			return existe;
		}

}
