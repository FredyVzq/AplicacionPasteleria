package Controlador;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Modelo.DAOConexion;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import Modelo.DAOUsuario;

public class ControladorMenu  implements Initializable{
	@FXML Button btnProductos,btnSalir,btnUsuarios,btnCategorias,btnMarcas,btnVenta;
	@FXML Label lblUsuario,lblHora,lblEquipo;
	private ControladorVentanas ins;
	@SuppressWarnings("unused")
	private DAOConexion con;
	DAOUsuario usuario;
	String nombre;

	public ControladorMenu(){
		ins = ControladorVentanas.getInstancia();
		con = new DAOConexion();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuario = (DAOUsuario) ins.getPrimaryStage().getUserData();
		nombre=usuario.getNomUsuario();
		lblUsuario.setText(nombre);
		lblHora.setText((new Date()).toString());
		lblEquipo.setText(System.getProperty("user.name"));
		permitir();
	}
	public void permitir(){
		ObservableList<DAOUsuario> lista=usuario.obtenerNivel();
		String nivel=lista.get(0).getNivel();
				if  (nivel.equals("inventario")) {
					btnUsuarios.setDisable(true);
					btnVenta.setDisable(true);
				}else{
					if (nivel.equals("ventas")) {
					btnUsuarios.setDisable(true);
					btnMarcas.setDisable(true);
					btnProductos.setDisable(true);
					btnCategorias.setDisable(true);
					}else{
						btnUsuarios.setDisable(false);
						btnVenta.setDisable(false);
					}
			}
	}
	@FXML public void clickUsuarios(){
		ins.asignarModal("/Vistas/Usuarios.fxml", "Usuarios");
	}
	@FXML public void clickProductos(){
		ins.asignarModal("/Vistas/Productos.fxml","Productos");
	}
	@FXML public void clickMarcas(){
		ins.asignarModal("/Vistas/Marca.fxml","Marcas de productos");
	}
	@FXML public void clickCategorias(){
		ins.asignarModal("/Vistas/Categoria.fxml","Categorias de productos");
	}
	@FXML public void clickBases(){
		ins.asignarModal("/Vistas/Bases.fxml","Bases");
	}
	@FXML public void clickClientes(){
		ins.asignarModal("/Vistas/Clientes.fxml","Clientes");
	}
	@FXML public void clickPedidos(){
		ins.asignarModal("/Vistas/Pedidos.fxml","Pedidos");
	}
	@FXML public void clickVentas(){
		ins.asignarModal("/Vistas/Venta.fxml","Ventas");
	}
	@FXML public void clickInventario(){
		ins.asignarModal("/Vistas/Inventario.fxml","Inventario");
	}
	@FXML public void salir(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmar");
    	alert.setHeaderText("¿Desea salir de la aplicación?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    System.exit(0);
    	} else {
    		alert.close();
    	}
	}
	   public String getNombre(){
	    	return nombre;
	    }
}
