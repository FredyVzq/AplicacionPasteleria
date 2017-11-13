package Controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Modelo.DAOCategoria;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorCategorias implements Initializable{

	@FXML TextField tfNombre;
	@FXML TextField tfId;
	@FXML Label nregistros;
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOCategoria> tablaCategoria;
	ObservableList<DAOCategoria> listaCategoria;
	@SuppressWarnings("unused")
	private ControladorVentanas ins;
	DAOCategoria categoria;
	ControladorLog log;
	String usuariologeado;
	ControladorMenu usuario;
	public ControladorCategorias() {
		log=new ControladorLog();
		usuariologeado="";
		categoria=new DAOCategoria();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuario =new ControladorMenu();
		usuariologeado=usuario.getNombre();
		listaCategoria=categoria.mostrar();
		tablaCategoria.setItems(categoria.mostrar());
		tfNombre.setDisable(true);
		int r=listaCategoria.size();
		String reg=Integer.toString(r);
		nregistros.setText(reg);
		restricciones();
	}
	public void restricciones(){
		tfNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,25}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
	}


	@FXML public void clickNuevo(){
		tfNombre.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
	}

	@FXML public void clickCancelar(){
		tfNombre.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		tfNombre.setText("");
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
    			log.nuevo(usuariologeado,"Categoria",tfNombre.getText() );
    			clickCancelar();

        	}
        	else{
        		System.out.println("Error al insertar los datos");
        	}
		}
	}
	@FXML public void clickTableView(){
		if(tablaCategoria.getSelectionModel().getSelectedItem() != null){
			categoria = tablaCategoria.getSelectionModel().getSelectedItem();
			tfNombre.setText(categoria.getNombre());
			tfId.setText(String.valueOf(categoria.getIdCategoria()));
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			btnEditar.setDisable(false);
			tfNombre.setDisable(false);
			btnEliminar.setDisable(false);
			btnCancelar.setDisable(false);;
		}
			else{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Por favor seleccione un registro de la tabla.");
				alert.showAndWait();
			}

	}
	@FXML public void clickEditar(){
		if(tfNombre.getText().trim().isEmpty()){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Datos faltantes");
				alert.setHeaderText(null);
				alert.setContentText("Por favor llena todos los campos.");
				alert.showAndWait();
			}
			else{
				btnCancelar.setDisable(false);
				boolean confirmar2=false;
				if(confirmar2==false){
						this.categoria.setNombre(tfNombre.getText());
						this.categoria.setIdCategoria(Integer.parseInt(tfId.getText()));

						if(categoria.editar()==true){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Cambio realizado!");
							alert.setHeaderText(null);
							alert.setContentText("Se han modificado los datos exitosamente");
							alert.showAndWait();
							listaCategoria=categoria.mostrar();
							tablaCategoria.setItems(categoria.mostrar());
							log.editado(usuariologeado,"Categoria",tfNombre.getText() );
							clickCancelar();

						}
						else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("La información no se ha podido editar.");
							alert.showAndWait();
				}
			}
		}
	}
	@FXML public void clickEliminar(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmar");
    	alert.setHeaderText("¿Desea eliminar el registro?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
        	this.categoria.setIdCategoria(Integer.parseInt(tfId.getText()));
            this.categoria.eliminar();
            log.eliminado(usuariologeado,"Categoria",tfNombre.getText());
    		listaCategoria=categoria.mostrar();
			tablaCategoria.setItems(categoria.mostrar());
			clickCancelar();
    	}
    	else{
    		alert.close();
    	}

	}
}

