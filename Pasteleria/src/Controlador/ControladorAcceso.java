package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.beans.property.StringProperty;
import Modelo.DAOUsuario;
import Controlador.notificaciones.Notification;

public class ControladorAcceso implements Initializable{

	@FXML TextField txtNomUsuario;
    @FXML PasswordField pfContrasenia;
    private DAOUsuario usuario;
    private ControladorVentanas cv;
    @FXML Label ayuda;
    ControladorLog log;
    public String usuarioActivo;

    public ControladorAcceso(){
        this.usuario = new DAOUsuario();
        cv = ControladorVentanas.getInstancia();
        log=new ControladorLog();
        usuarioActivo="";
    }
    public String getUsuarioActivo() {
		return usuarioActivo;
	}
	public void setUsuarioActivo(String usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
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
            	 Notification.Notifier.INSTANCE.notifyInfo("Pastelería Jessy", "Datos de usuarios no válidos.");
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
                    cv.asignarMenu("/Vistas/MenuPrincipal.fxml", "Bienvenido "+ temp.getNomUsuario().toUpperCase(), temp);
                    setUsuarioActivo(txtNomUsuario.getText());
                    log.acceso(txtNomUsuario.getText());

                }
                else{
                    Notification.Notifier.INSTANCE.notifyError("Pastelería Jessy", "Datos de usuarios no válidos.");
                    int i=(int)(Math.random()*4);
                    switch (i) {
					case 1:ayuda.setText("¡Accede con el nombre!");break;
					case 2:ayuda.setText("¿Estas seguro?");break;
					case 3:ayuda.setText("¿Recuerdas bien?");break;
					case 4:ayuda.setText("Datos erroneos");break;
					default:ayuda.setText("Bienvenido");break;
					}
                	txtNomUsuario.clear();
                    pfContrasenia.clear();
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}