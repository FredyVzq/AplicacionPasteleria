package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Modelo.DAOCategoria;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorCategorias implements Initializable{

	@FXML TextField tfNombre;
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOCategoria> tablaCategoria;
	ObservableList<DAOCategoria> listaCategoria;
	DAOCategoria categoria;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		categoria=new DAOCategoria();
		listaCategoria=categoria.mostrar();
		tablaCategoria.setItems(categoria.mostrar());
	}


	@FXML public void clickNuevo(){
		tfNombre.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
	}

	@FXML public void clickCancelar(){

		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
	}
	@FXML public void clickGuardar(){

		if(tfNombre.getText().isEmpty()){

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ingrese Datos");
		alert.setHeaderText("Campos Vacios");
    	alert.setContentText("Por favor ingrese la informacion solicitada!");
    	alert.showAndWait();

		}else{
			categoria.setNombre(tfNombre.getText());
			if(categoria.insertar()==true){
        		System.out.println("Se insertaron los datos correctamente");
        		Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Informacion Agregada");
    			alert.setHeaderText(null);
    			alert.setContentText("La informacion se ha guardado de forma exitosa!");
    			alert.showAndWait();
    			//Actualiza la tabla
    			listaCategoria=categoria.mostrar();
    			tablaCategoria.setItems(categoria.mostrar());
        	}
        	else{
        		System.out.println("Error al insertar los datos");
        	}
		}
	}
}

