package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ControladorConfirmacion implements Initializable{
	@FXML Button aceptar,cancelar;
	private boolean res;
	ControladorProductos aProd;

	public ControladorConfirmacion(){
		res= false;
		aProd= new ControladorProductos();
	}

	@Override
	public void initialize(URL arg0,ResourceBundle arg1){

	}

	@FXML public void Aceptar(){
		//aProd.Confirmacion(res);
	}
	@FXML public void Cancelar(){

	}

}
