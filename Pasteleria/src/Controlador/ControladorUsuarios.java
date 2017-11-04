package Controlador;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.ResourceBundle;

import com.itextpdf.text.pdf.Pfm2afm;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOUsuario;

public class ControladorUsuarios implements Initializable{
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	@FXML TextField txtAlias, txtId;
	@FXML PasswordField pswContrasenia;
	@FXML PasswordField pswRepContrasenia;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOUsuario> tablaUsuarios;
	@FXML ComboBox<String>cbNivel;
	DAOUsuario datosUsuarios;
	ControladorLog log;
	String usuarioLogueado;
	ObservableList<DAOUsuario> listaUsuario;

	public ControladorUsuarios(){
		datosUsuarios=new DAOUsuario();
		log = new ControladorLog();
		usuarioLogueado="";
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbNivel.getItems().addAll("administrador","inventario","ventas");
		listaUsuario=datosUsuarios.mostrar();
		tablaUsuarios.setItems(datosUsuarios.mostrar());
	}
	@FXML public void actualizar(){
		listaUsuario=datosUsuarios.mostrar();
		tablaUsuarios.setItems(datosUsuarios.mostrar());
	}
	@FXML public void clickNuevo(){
		txtAlias.setDisable(false);
		pswContrasenia.setDisable(false);
		pswRepContrasenia.setDisable(false);
		btnGuardar.setDisable(false);
		btnNuevo.setDisable(true);
		btnCancelar.setDisable(false);
		cbNivel.setDisable(false);
	}
	@FXML public void clickCancelar(){
		txtAlias.setDisable(true);txtAlias.clear();
		pswContrasenia.setDisable(true);pswContrasenia.clear();
		pswRepContrasenia.setDisable(true);pswRepContrasenia.clear();
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		cbNivel.setDisable(true);cbNivel.getSelectionModel().clearSelection();
	}
	@FXML public void clickGuardar(){
		String contra=pswContrasenia.getText();
		String contra2=pswRepContrasenia.getText();
		//Alerta de Campos Vacios
		if(txtAlias.getText().isEmpty()||pswContrasenia.getText().isEmpty()||pswRepContrasenia.getText().isEmpty()){

			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Ingresar datos");
	    	alert.setHeaderText("Campos vacios");
	    	alert.setContentText("Por favor ingrese la informacion solicitada!");
	    	alert.showAndWait();

		}else{
			//Comparar contraseñas
			if(cbNivel.getSelectionModel().getSelectedItem()==null){
				
				Alert alert = new Alert(AlertType.WARNING);
		    	alert.setTitle("Ingresar datos");
		    	alert.setHeaderText("Usuarios");
		    	alert.setContentText("¡Por favor elija el tipo de usuario a registrar!");
		    	alert.showAndWait();

					}else{
						
						if(contra==contra2){
							Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Ingresar datos");
					    	alert.setHeaderText("Contraseña");
					    	alert.setContentText("Por favor asegurese de escribir correctamente su contraseña");
					    	alert.showAndWait();
					    	System.out.println("help me");
						}else{
							datosUsuarios.setNomUsuario(txtAlias.getText());
							datosUsuarios.setContrasenia(pswContrasenia.getText());
							datosUsuarios.setNivel(cbNivel.getSelectionModel().getSelectedItem());
							System.out.println(":v");
						if(datosUsuarios.insertar()==true){
							listaUsuario = datosUsuarios.mostrar();
							tablaUsuarios.setItems(datosUsuarios.mostrar());
							log.nuevo(usuarioLogueado, "Registro de usuario", txtAlias.getText());
							btnNuevo.setDisable(false);
							btnEditar.setDisable(true);
							btnEliminar.setDisable(true);
							txtAlias.setText("");txtAlias.setDisable(true);
							pswContrasenia.setText("");pswContrasenia.setDisable(true);
							pswRepContrasenia.setText("");pswRepContrasenia.setDisable(true);
							cbNivel.getItems().clear();cbNivel.setDisable(true);
							System.out.println("Se insertaron correctamente los datos");
							actualizar();
							btnCancelar.setDisable(true);
							btnGuardar.setDisable(true);
							Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Datos correctos");
					    	alert.setHeaderText("Correctos");
					    	alert.setContentText("Se insertaron correctamente los datos");
					    	alert.showAndWait();
						}else{
							System.out.println("Error al insertar los datos");
						}
						
						 }
					}
				}
	}
	@FXML public void clickTableView(){
		if (tablaUsuarios.getSelectionModel().getSelectedItem()!=null) {
			datosUsuarios = tablaUsuarios.getSelectionModel().getSelectedItem();
			txtAlias.setText(String.valueOf(datosUsuarios.getNomUsuario()));
			pswContrasenia.setText(String.valueOf(datosUsuarios.getContrasenia()));
			pswRepContrasenia.setText(String.valueOf(datosUsuarios.getContrasenia()));
			txtId.setText(String.valueOf(datosUsuarios.getIdUsuario()));
			txtAlias.setDisable(false);
			pswContrasenia.setDisable(false);
			pswRepContrasenia.setDisable(false);
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			cbNivel.setDisable(false);
			btnEditar.setDisable(false);
			btnCancelar.setDisable(false);
		}
	}
	@FXML public void clickBorrar(){

	}
	@FXML public void clickEditar(){
		String contra=pswContrasenia.getText();
		String contra2=pswRepContrasenia.getText();
		if (txtAlias.getText().isEmpty()||pswContrasenia.getText().isEmpty()||pswRepContrasenia.getText().isEmpty()||cbNivel.getSelectionModel().getSelectedItem().isEmpty()) {
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Confirme",
					"Por favor asegurese de completar todos los campos.", WARNING_ICON);
		}if (contra==contra2) {
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Contraseñas",
					"Las contraseñas no coinciden, por favor asegurese de que sean iguales.", WARNING_ICON);
		}else{
			boolean confirmar2=false;
			if(confirmar2==false){
				this.datosUsuarios.setNomUsuario(txtAlias.getText());
				this.datosUsuarios.setContrasenia(pswContrasenia.getText());
				this.datosUsuarios.setNivel(cbNivel.getSelectionModel().getSelectedItem());
				System.out.println(datosUsuarios.getIdUsuario());
			}if (datosUsuarios.editar()==true) {
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Modificados",
						"La información ha sido actualizada", SUCCESS_ICON);
				actualizar();
				tablaUsuarios.refresh();
			}else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Error",
						"La informacion no se ha podido editar, por favor intentelo de nuevo", WARNING_ICON);
			}
		}
	}
}
