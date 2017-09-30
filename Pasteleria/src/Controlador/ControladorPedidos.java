package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOBases;
import Modelo.DAOCliente;
import Modelo.DAOProducto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class ControladorPedidos {
	@SuppressWarnings("unused")
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	@FXML TextField lbCodigo;
	@FXML TextField lbImporBase;
	@FXML TextField lbTotal;
	@FXML TextField lbAnticipo;
	@FXML TextField lbDebiendo;
	@FXML TextField lbBase;
	@FXML ComboBox<DAOCliente> cbNombreCliente;
	@FXML ComboBox<DAOProducto> cbPastYpost;
	@FXML ComboBox<DAOBases> cbBases;
	@FXML ChoiceBox<DAOProducto> cbProdGen;
	@FXML Button btnNuevo;
	@FXML Button btnNuevoProd;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	public DAOCliente infCliente;
	public DAOProducto infProducto;
	public DAOBases infBases;
	ObservableList<DAOCliente> listaClientes;


	public ControladorPedidos(){
		infCliente=new DAOCliente();
		infProducto=new DAOProducto();
		infBases=new DAOBases();
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnNuevo.setDisable(false);
		listaClientes=infCliente.nombreCliente();
		cbNombreCliente.setItems(listaClientes);
	}
	@FXML public void clickNuevo(){
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		lbCodigo.setDisable(false);
		cbNombreCliente.setDisable(false);
		cbBases.setDisable(false);
		cbPastYpost.setDisable(false);
		lbImporBase.setDisable(false);
		lbTotal.setDisable(false);
	    lbAnticipo.setDisable(false);
		lbDebiendo.setDisable(false);
		lbBase.setDisable(false);
	}
	@FXML public void clickCancelar(){
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEditar.setDisable(true);
		lbCodigo.setDisable(true);
		cbNombreCliente.setDisable(true);
		cbBases.setDisable(true);
		cbPastYpost.setDisable(true);
		lbImporBase.setDisable(true);
		lbTotal.setDisable(true);
	    lbAnticipo.setDisable(true);
		lbDebiendo.setDisable(true);
		lbBase.setDisable(true);
	}
	@FXML public void clickGuardar(){
		if(lbCodigo.getText().trim().isEmpty()||cbNombreCliente.getSelectionModel().getSelectedItem()==null){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Datos faltantes");
			alert.setHeaderText(null);
			alert.setContentText("Por favor llena todos los campos.");
			alert.showAndWait();
		}else{

		}
	}
}
