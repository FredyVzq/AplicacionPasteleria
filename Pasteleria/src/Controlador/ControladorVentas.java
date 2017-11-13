	package Controlador;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOCliente;
import Modelo.DAOConexion;
import Modelo.DAODetVentas;
import Modelo.DAOProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class ControladorVentas implements Initializable {
	DAOConexion con;
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	@FXML Button btnBuscar,btnNuevo,btnCancelar,btnEditar, btnEliminar,btnClientes,btnInventario;
	@FXML RadioButton rbPublico, rbClientes;
	@FXML ComboBox<DAOCliente> cbClientes;
	@FXML TextField codigo,cantidad,precio,producto;
	@FXML TableView <DAOProducto> tabla;
	@FXML Label totalC;
	@FXML CheckBox ckPrecio;
	String productos;
	DAOCliente dc;
	private ControladorVentanas ins;
    private ObservableList<DAOProducto> detalle;
    ObservableList<DAOCliente> listaClientes;
    DAODetVentas dv;
    public ControladorVentas(){
		con = new DAOConexion();
		dc = new DAOCliente();
		//dv = new DAODetVentas();
		ins = ControladorVentanas.getInstancia();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaClientes = dc.mostrar();
		con.conectar();
		detalle= FXCollections.observableArrayList();
		cbClientes.setItems(listaClientes);
	}
	@FXML public void clickNuevo(){
		btnBuscar.setDisable(false);
		btnCancelar.setDisable(false);
		codigo.setDisable(false);
		btnNuevo.setDisable(true);
		cantidad.setDisable(false);
		rbPublico.setDisable(false);
		rbClientes.setDisable(false);
		ckPrecio.setDisable(false);
		btnClientes.setDisable(false);
		btnInventario.setDisable(false);
	}
	@FXML public void cancelar(){
		btnBuscar.setDisable(true);
		btnCancelar.setDisable(true);
		codigo.setDisable(true);
		btnNuevo.setDisable(false);
		cantidad.setDisable(true);
		rbPublico.setDisable(true);
		rbClientes.setDisable(true);
		ckPrecio.setDisable(true);
		ckPrecio.setSelected(false);
		btnClientes.setDisable(true);
		btnInventario.setDisable(true);
		precio.setDisable(true);
		tabla.setItems(null);
		producto.clear();
		codigo.setText("");
		precio.setText("");
		cantidad.setText("1");
		totalC.setText("0.00");
	}
	@FXML public void busqueda(){
		DecimalFormat df=new DecimalFormat("###0.00");
		DAOProducto prod;
		prod = new DAOProducto();
		prod.consultar(codigo.getText());
		if(prod.getExiste()){
			prod.setCantidadVenta(Integer.parseInt(cantidad.getText()));
			prod.setPrecio(Double.parseDouble(precio.getText()));

			double total=Integer.parseInt(cantidad.getText())*prod.getPrecio();
			double tCalculado=Double.parseDouble(totalC.getText())+total;

			totalC.setText(String.valueOf(df.format(tCalculado)));
			detalle.add(prod);
			tabla.setItems(detalle);
			tabla.refresh();
		}else{
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Producto no encontrado",
					"El c�digo no fue encontrado o sin existencias", WARNING_ICON);
		}
		tabla.refresh();
	}
	@FXML public void irClientes(){
		ins.asignarModal("/Vistas/Clientes.fxml","Clientes");
	}
	@FXML public void irInventario(){
		ins.asignarModal("/Vistas/Inventario.fxml","Inventario");
	}
	@FXML public void elegirCliente(){
		cbClientes.setDisable(false);
	}
	@FXML public void elegirPublico(){
		cbClientes.setDisable(true);
		listaClientes = dc.mostrar();
		cbClientes.setItems(listaClientes);
	}
	@FXML public void selecCortesia(){
		if(ckPrecio.isSelected()){
			precio.setDisable(false);
		}else{
			precio.setDisable(true);
		}
	}
	/*@FXML public void cambiarPrecio(){
		DAOProducto prod;
		prod = new DAOProducto();

		detalle.add(prod);
		tabla.setItems(detalle);
		tabla.refresh();

		DecimalFormat df=new DecimalFormat("###0.00");
		prod.consultar(codigo.getText());
		if(prod.getExiste()){
			prod.setCantidadVenta(Integer.parseInt(cantidad.getText()));
			prod.setPrecio(Double.parseDouble(precio.getText()));
			//precio.setText(String.valueOf(prod.getPrecio()));

			double total=Integer.parseInt(cantidad.getText())*prod.getPrecio();
			double tCalculado=Double.parseDouble(totalC.getText())+total;

			totalC.setText(String.valueOf(df.format(tCalculado)));
			detalle.add(prod);
			tabla.setItems(detalle);
			tabla.refresh();
		}else{
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Producto no encontrado",
					"El c�digo no fue encontrado o sin existencias", WARNING_ICON);
		}
		//tabla.refresh();
	}*/
	@FXML public void pulsarEnter(KeyEvent e){
		DAOProducto prod;
		prod = new DAOProducto();
		prod.consultar(codigo.getText());
		if(e.getCode()==KeyCode.ENTER){
			System.out.println("Hola");
			if(prod.getExiste()){
				prod.setCantidadVenta(Integer.parseInt(cantidad.getText()));
				precio.setText(String.valueOf(prod.getPrecio()));
				producto.setText(String.valueOf(prod.getNombre()));
			}else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Producto no encontrado",
						"El c�digo no fue encontrado o sin existencias", WARNING_ICON);
			}
		}
	}
	@FXML public void venta(){
		
	}
}
