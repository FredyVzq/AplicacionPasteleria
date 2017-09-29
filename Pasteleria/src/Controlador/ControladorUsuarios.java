package Controlador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

import Modelo.DAOUsuario;

public class ControladorUsuarios implements Initializable{
	@FXML TextField txtAlias;
	@FXML PasswordField pswContrasenia;
	@FXML PasswordField pswRepContrasenia;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML Button btnAgregar;
	@FXML ComboBox<String>cbNivel;
	DAOUsuario datosUsuarios;

	public ControladorUsuarios(){
		datosUsuarios=new DAOUsuario();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbNivel.getItems().addAll("aministrador","usuario");
	}
	@FXML public void clickNuevo(){
		txtAlias.setDisable(false);
		pswContrasenia.setDisable(false);
		pswRepContrasenia.setDisable(false);
		btnGuardar.setDisable(false);
		btnNuevo.setDisable(true);
		btnCancelar.setDisable(false);
		btnAgregar.setDisable(false);
		cbNivel.setDisable(false);
	}
	@FXML public void clickCancelar(){
		txtAlias.setDisable(true);
		pswContrasenia.setDisable(true);
		pswRepContrasenia.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnAgregar.setDisable(true);
		cbNivel.setDisable(true);
	}
	@FXML public void clickGuardar(){
		String contra=pswContrasenia.getText();
		String contra2=pswRepContrasenia.getText();
		//String txtAd="Administrador";
		//String txtUsr="Usuario";
		//Alerta de Campos Vacios
		if(txtAlias.getText().isEmpty()||pswContrasenia.getText().isEmpty()||pswRepContrasenia.getText().isEmpty()){

			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Ingresar Datos");
	    	alert.setHeaderText("Campos Vacios");
	    	alert.setContentText("Por favor ingrese la informacion solicitada!");
	    	alert.showAndWait();

		}else{
			//Comparar contraseñas
			if(contra.equals(contra2)){
							datosUsuarios.setNomUsuario(txtAlias.getText());
							datosUsuarios.setContrasenia(pswContrasenia.getText());
							pswContrasenia.setDisable(true);
							pswRepContrasenia.setDisable(true);
							datosUsuarios.setNivel(cbNivel.getSelectionModel().getSelectedItem().toString());
							btnNuevo.setDisable(false);
							btnCancelar.setDisable(true);
							btnGuardar.setDisable(true);
							btnAgregar.setDisable(true);
							txtAlias.setText("");
							pswContrasenia.setText("");
							pswRepContrasenia.setText("");
							cbNivel.setDisable(true);
							/*clickNuevo();
					    	txtNombre.setText("");
							txtApePat.setText("");
							txtApeMat.setText("");
							txtAlias.setText("");*/

					}else{
						if(cbNivel.getSelectionModel().getSelectedItem()==null||cbNivel.getSelectionModel().getSelectedItem()==null){
							Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Ingresar Datos");
					    	alert.setHeaderText("Usuarios");
					    	alert.setContentText("Por favor elija el tipo de Usuario a Registrar!");
					    	alert.showAndWait();
					    	pswContrasenia.setText("");
							pswRepContrasenia.setText("");
						}
						else{
							txtAlias.setDisable(true);
							pswContrasenia.setDisable(true);
							pswRepContrasenia.setDisable(true);
							btnNuevo.setDisable(false);
							btnCancelar.setDisable(true);
							btnGuardar.setDisable(true);
							btnAgregar.setDisable(true);
							txtAlias.setText("");
							pswContrasenia.setText("");
							pswRepContrasenia.setText("");
							cbNivel.setDisable(true);
						}if(datosUsuarios.insertar()==true){
							System.out.println("Se insertaron correctamente los datos");
							Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Datos correctos");
					    	alert.setHeaderText("Correcots");
					    	alert.setContentText("Se insertaron Correctamente los datos");
					    	alert.showAndWait();
						}else{
							System.out.println("Error al insertar los datos");
						}
						//Alerta de Contraseñas Incorrectas
							Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Ingresar Datos");
					    	alert.setHeaderText("Contraseñas Errones");
					    	alert.setContentText("Por favor verifique las contraseñas!");
					    	alert.showAndWait();
					    	pswContrasenia.setText("");
							pswRepContrasenia.setText("");
						 }
				}

	}
	@FXML public void clickBorrar(){

	}
	@FXML public void clickEditar(){

	}
}
