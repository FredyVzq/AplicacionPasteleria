package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOBases;
import Modelo.DAOCliente;
import Modelo.DAOProducto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ControladorPedidos implements Initializable{
	@SuppressWarnings("unused")
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	//Botones generales
	@FXML Button btnNuevo,btnGuardar,btnCancelar;
	//Seccion Cliente
	@FXML ComboBox<DAOCliente> cbNombreCliente;
	@FXML TextField direccionClnt,telefonoClnt;
	//Seccion Productos
	@FXML TextField txtCodigo,txtProducto,txtExistencias,txtPrecio,txtCantidad,txtTotal;
	@FXML Button agregar;
	//Informacion general
	@FXML TextField folio,precioBase,total,importe,adeudo;
	@FXML ComboBox<DAOBases> cbBases;
	private DAOCliente infCliente;
	private DAOBases infBases;
	ObservableList<DAOCliente> listaClientes;
	ObservableList<DAOBases> listaBases;

	public ControladorPedidos(){
		infCliente=new DAOCliente();
		infBases=new DAOBases();
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnNuevo.setDisable(false);
		listaClientes=infCliente.mostrar();
		cbNombreCliente.setItems(listaClientes);
		listaBases=infBases.mostrar();
		cbBases.setItems(listaBases);
	}
	@FXML public void clickNuevo(){
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		cbNombreCliente.setDisable(false);
		direccionClnt.setDisable(false);
		telefonoClnt.setDisable(false);
		txtCodigo.setDisable(false);
		txtProducto.setDisable(false);
		txtExistencias.setDisable(false);
		txtPrecio.setDisable(false);
		txtCantidad.setDisable(false);
		txtTotal.setDisable(false);
		agregar.setDisable(false);
		folio.setDisable(false);
		precioBase.setDisable(false);
		total.setDisable(false);
		importe.setDisable(false);
		adeudo.setDisable(false);
	    cbBases.setDisable(false);
	}
	@FXML public void clickCancelar(){
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		cbNombreCliente.setDisable(true);
		direccionClnt.setDisable(true);
		telefonoClnt.setDisable(true);
		txtCodigo.setDisable(true);
		txtProducto.setDisable(true);
		txtExistencias.setDisable(true);
		txtPrecio.setDisable(true);
		txtCantidad.setDisable(true);
		txtTotal.setDisable(true);
		agregar.setDisable(true);
		folio.setDisable(true);
		precioBase.setDisable(true);
		total.setDisable(true);
		importe.setDisable(true);
		adeudo.setDisable(true);
	    cbBases.setDisable(true);
	    direccionClnt.setText("");
	    telefonoClnt.setText("");
	    txtCodigo.setText("");
	    txtProducto.setText("");
	    txtExistencias.setText("");
	    txtPrecio.setText("");
	    txtCantidad.setText("");
	    txtTotal.setText("");
	    folio.setText("");
	    precioBase.setText("");
	    total.setText("");
	    importe.setText("");
	    adeudo.setText("");
	}
	@FXML public void pulsarEnter(KeyEvent e){
		DAOProducto prod;
		prod = new DAOProducto();
		prod.consultar(txtCodigo.getText());
		if(e.getCode()==KeyCode.ENTER){
			if(prod.getExiste()){
				txtPrecio.setText(String.valueOf(prod.getPrecio()));
				txtProducto.setText(String.valueOf(prod.getNombre()));
				txtExistencias.setText(String.valueOf(prod.getCantidad()));
			}else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Producto no encontrado",
						"El código no fue encontrado o sin existencias", WARNING_ICON);
			}
		}
	}
	@FXML public void pulsarEnterBases(KeyEvent e){
		DAOBases bas;
		bas = new DAOBases();
		bas.consultar(String.valueOf(cbBases.getSelectionModel().getSelectedItem()));
		if(e.getCode()==KeyCode.ENTER){
			if(bas.getExiste()){
				precioBase.setText(String.valueOf(bas.getPrecio()));
			}else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Error",
						"No fue posible cargar los datos", WARNING_ICON);
			}
		}
	}
}
