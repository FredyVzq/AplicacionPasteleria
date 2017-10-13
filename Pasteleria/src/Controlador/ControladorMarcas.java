package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Modelo.DAOMarcas;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorMarcas implements Initializable{
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML Button actualizar;
	@FXML TextField tfNombre;
	@FXML TextField tfProveedor;
	@FXML TableView<DAOMarcas> tableViewR;
	ObservableList<DAOMarcas> listadeMarcas;
	DAOMarcas datosMarcas;

	public ControladorMarcas() {
		datosMarcas=new DAOMarcas();
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
		listadeMarcas=datosMarcas.mostrar();
		tableViewR.setItems(listadeMarcas);
	}
	@FXML public void clickNuevo(){
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		tfNombre.setDisable(false);
		tfProveedor.setDisable(false);
	}
	@FXML public void clickCancelar(){
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		tfNombre.setDisable(true);
		tfProveedor.setDisable(true);
	}
	@FXML public void clickGuardar(){
		String pro=tfProveedor.getText();
		if(tfNombre.getText().isEmpty()||tfProveedor.getText().isEmpty()){
			Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Ingresar Datos");
	    	alert.setHeaderText("Campos Vacios");
	    	alert.setContentText("Por favor ingrese la informacion solicitada!");
	    	alert.showAndWait();
		}
		else{
			//Se manda la informacion a la base de datos
			datosMarcas.setNombre(tfNombre.getText());
        	datosMarcas.setProveedor(pro);
        	if(datosMarcas.insertar()==true){
        		System.out.println("Se insertaron los datos correctamente");
        		tfNombre.setText("");
        		tfProveedor.setText("");
        		Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Informacion Agregada");
    			alert.setHeaderText(null);
    			alert.setContentText("La informacion se ha guardado de forma exitosa!");
    			alert.showAndWait();
    			listadeMarcas=datosMarcas.mostrar();
    			tableViewR.setItems(datosMarcas.mostrar());
        	}
        	else{
        		System.out.println("Error al insertar los datos");
        	}
		}
	}
	@FXML public void actualizar(){
		listadeMarcas=datosMarcas.mostrar();
		tableViewR.setItems(datosMarcas.mostrar());
	}
	@FXML public void clickEliminar(){}
	@FXML public void clickModificar(){}
}
