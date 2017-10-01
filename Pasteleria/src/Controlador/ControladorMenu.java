package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Modelo.DAOConexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Modelo.DAOUsuario;

public class ControladorMenu  implements Initializable{
	@FXML Button btnProductos,btnSalir,btnUsuarios,btnCategorias,btnMarcas,btnVenta;
	@FXML Label lblUsuario,lblHora;
	private ControladorVentanas ins;
	@SuppressWarnings("unused")
	private DAOConexion con;



	public ControladorMenu(){
		ins = ControladorVentanas.getInstancia();
		con = new DAOConexion();

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	@FXML public void clickUsuarios(){
		@SuppressWarnings("unused")
		DAOUsuario usuario = (DAOUsuario) ins.getPrimaryStage().getUserData();
		ins.asignarModal("../Vistas/Usuarios.fxml", "Usuarios");
	}
	@FXML public void clickProductos(){
		ins.asignarModal("../Vistas/Productos.fxml","Productos");
	}
	@FXML public void clickMarcas(){
		ins.asignarModal("../Vistas/Marca.fxml","Marcas de productos");
	}
	@FXML public void clickCategorias(){
		ins.asignarModal("../Vistas/Categoria.fxml","Categorias de productos");
	}
	@FXML public void clickBases(){
		ins.asignarModal("../Vistas/Bases.fxml","Bases");
	}
	@FXML public void clickClientes(){
		ins.asignarModal("../Vistas/Clientes.fxml","Clientes");
	}
	@FXML public void clickPedidos(){
		ins.asignarModal("../Vistas/Pedidos.fxml","Pedidos");
	}
	@FXML public void clickVentas(){
		ins.asignarModal("../Vistas/Venta.fxml","Ventas");
	}
	@FXML public void salir(){
		Icon icono = new ImageIcon(getClass().getResource("../Vistas/images/exit.png"));
		int resultado = JOptionPane.showConfirmDialog(null, "Desea salir de la Aplicación?", "Confirmar salida", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,icono);

				if (JOptionPane.OK_OPTION == resultado){
					System.exit(0);
					System.out.println("Aplicacion terminada");
				}
				else{}
	}

}
