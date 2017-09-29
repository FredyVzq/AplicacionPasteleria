package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.StringProperty;
import Modelo.DAOUsuario;
import Controlador.notificaciones.Notification;

public class ControladorAcceso implements Initializable{

	@FXML TextField txtNomUsuario;
    @FXML PasswordField pfContrasenia;
    private DAOUsuario usuario;
    private ControladorVentanas cv;

    public ControladorAcceso(){
        this.usuario = new DAOUsuario();
        cv = ControladorVentanas.getInstancia();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	txtNomUsuario.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9]{0,20}") || newValue.length()>20)
	            ((StringProperty)observable).setValue(oldValue);//Se regresa al valor anterior
	        else
	            ((StringProperty)observable).setValue(newValue);//Se asigna el nuevo valor, porque es válido.
    	});
    }

    @FXML public void clickValidar(){
        try{
            if(txtNomUsuario.getText().trim().isEmpty() || pfContrasenia.getText().trim().isEmpty()){
            	Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Ingresar Datos");
    	    	alert.setHeaderText("Campos Vacios");
    	    	alert.showAndWait();
            }
            else{
                usuario.setNomUsuario(txtNomUsuario.getText());
                usuario.setContrasenia(pfContrasenia.getText());
                DAOUsuario temp = usuario.validarDatos();
                if(temp!=null){
                	//Para cerrar la ventana de inicio de sesión
                    cv.cerrarAcceso();
                    //Para cerrar el stage de la notificación
                    Notification.Notifier.INSTANCE.stop();
                    //Para visualizar la ventana de menú
                    cv.asignarModal("../Vistas/MenuPrincipal.fxml", "Bienvenido");

                }
                else{
                    Notification.Notifier.INSTANCE.notifyError("Pastelería Jessy", "Datos de usuarios no válidos.");
                	txtNomUsuario.clear();
                    pfContrasenia.clear();
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}