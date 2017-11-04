package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import Modelo.DAOMarcas;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorMarcas implements Initializable {
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML Button actualizar;
	@FXML Label numMarcas;
	@FXML TextField idMarca;
	@FXML TextField tfNombre;
	@FXML TextField tfProveedor;
	@FXML TableView<DAOMarcas> tableViewR;
	ObservableList<DAOMarcas> listadeMarcas;
	DAOMarcas datosMarcas;
	ControladorLog log;
	String usuariologeado;
	ControladorMenu usuario;
	private ControladorVentanas ins;

	public ControladorMarcas() {
		datosMarcas=new DAOMarcas();
		log=new ControladorLog();
		usuariologeado="";
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
		listadeMarcas=datosMarcas.mostrar();
		System.out.println(listadeMarcas.size());
		tableViewR.setItems(datosMarcas.mostrar());
		int numero=listadeMarcas.size();
		String num=Integer.toString(numero);
		numMarcas.setText(num);
		usuario = new ControladorMenu();
		usuariologeado=usuario.getNombre();
		restricciones();
	}
	public void restricciones(){
		tfNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,25}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
		});
		tfProveedor.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,25}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
		});
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
		tfNombre.setText("");
		tfProveedor.setText("");
		idMarca.setText("");
		tfNombre.setDisable(true);
		tfProveedor.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);;
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
    			log.nuevo(usuariologeado,"Marca",tfNombre.getText() );
    		clickCancelar();
        	}
        	else{
        		System.out.println("Error al insertar los datos");
        	}
		}
	}
	@FXML public void actualizar(){
		listadeMarcas=datosMarcas.mostrar();
		tableViewR.setItems(listadeMarcas);
	}
	@FXML public void clickTableView(){
		if(tableViewR.getSelectionModel().getSelectedItem() != null){
			datosMarcas = tableViewR.getSelectionModel().getSelectedItem();
			tfNombre.setText(datosMarcas.getNombre());
			tfProveedor.setText(datosMarcas.getProveedor());
			idMarca.setText(String.valueOf(datosMarcas.getId()));
			btnCancelar.setDisable(false);
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			btnEditar.setDisable(false);
			tfNombre.setDisable(false);
			tfProveedor.setDisable(false);
			btnEliminar.setDisable(false);
		}
			else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Por favor seleccione un registro de la tabla.");
				alert.showAndWait();
			}

	}

	@FXML public void clickModificar(){
		if(tfNombre.getText().isEmpty() || tfProveedor.getText().isEmpty()){

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("DATOS FALTANTES");
				alert.setHeaderText(null);
				alert.setContentText("Por favor llena todos los campos.");
				alert.showAndWait();
			}
			else{
				boolean confirmar2=false;
				if(confirmar2==false){
						this.datosMarcas.setNombre(tfNombre.getText());
						this.datosMarcas.setProveedor(tfProveedor.getText());
						this.datosMarcas.setId(Integer.parseInt(idMarca.getText()));

						if(datosMarcas.editar()){
							Alert alert = new Alert(AlertType.INFORMATION);
					    	alert.setTitle("Datos Correctos!");
					    	alert.setHeaderText("Datos modificados");
					    	alert.setContentText("Datos modificados correctamente!");
					    	alert.showAndWait();
					    	listadeMarcas=datosMarcas.mostrar();
							tableViewR.setItems(datosMarcas.mostrar());
							log.editado(usuariologeado,"Marca",tfNombre.getText() );
							clickCancelar();

						}
						else{

					    	Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Error");
					    	alert.setHeaderText("Verifica los datos");
					    	alert.setContentText("Se ha presentado un problema, por favor verifique los datos. Si el problema persiste, contactar a RAMSI");
					    	alert.showAndWait();
						}
					}
				}
	}
	@FXML public void clickEliminar(){
		int confirmarEliminar = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este registro de datosMarcas?");

        if (confirmarEliminar == 0) {
        	this.datosMarcas.setId(Integer.parseInt(idMarca.getText()));
        	datosMarcas.eliminar();
        	log.eliminado(usuariologeado,"Marca",tfNombre.getText());
            System.out.println("Realizado Eliminado");
            actualizar();
            tfNombre.setText("");
            tfProveedor.setText("");
            btnNuevo.setDisable(false);
            btnEditar.setDisable(true);
            btnEliminar.setDisable(true);
	}
}
}
