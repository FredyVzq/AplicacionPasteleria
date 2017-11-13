package Controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import Modelo.DAOBases;
import Modelo.DAOUsuario;


public class ControladorBases implements Initializable{
	@FXML TextField txtNombre;
	@FXML TextField txtPrecio;
	@FXML TextField txtExistencia;
	@FXML TextField idbases;
	@FXML Label numeroBases;
	@FXML Button btnRefrescar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOBases> tablaBases;
	ObservableList<DAOBases> listaBases;
	DAOBases bases;
	private ControladorVentanas ins;
	String usuariologeado;
	ControladorLog log;
	DAOUsuario usuario;

	public  ControladorBases() {
		ins = ControladorVentanas.getInstancia();
		bases=new DAOBases();
		log=new ControladorLog();
		usuariologeado="";
		}

	public void initialize(URL location, ResourceBundle resources) {
		listaBases=bases.mostrar();
		tablaBases.setItems(bases.mostrar());
		int numero=listaBases.size();
		String num=Integer.toString(numero);
		numeroBases.setText(num);
		btnEliminar.setDisable(true);
		usuario = (DAOUsuario) ins.getPrimaryStage().getUserData();
		usuariologeado=usuario.getNomUsuario();
		restricciones();
	}
	public void restricciones(){
		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9]{0,50}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtPrecio.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,7}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		txtExistencia.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,7}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
	}
	@FXML public void actualizar(){
		listaBases=bases.mostrar();
		tablaBases.setItems(bases.mostrar());
	}

//-------------------------
	@FXML public void editar(){
		btnEditar.setDisable(false);
		btnCancelar.setDisable(false);
		txtExistencia.setDisable(false);
		txtNombre.setDisable(false);
		txtPrecio.setDisable(false);
	}
	@FXML public void clickEditar(){
		if(txtNombre.getText().trim().isEmpty() ||txtPrecio.getText().trim().isEmpty()||
				txtExistencia.getText().trim().isEmpty()
			){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Datos faltantes");
				alert.setHeaderText(null);
				alert.setContentText("Por favor llena todos los campos.");
				alert.showAndWait();
			}
			else{
				boolean confirmar2=false;
				if(confirmar2==false){
						this.bases.setNombre(txtNombre.getText());
						this.bases.setPrecio(txtPrecio.getText());
						this.bases.setExistencia(txtExistencia.getText());
						this.bases.setIdbases(Integer.parseInt(idbases.getText()));

						if(bases.editar()){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("DATOS MODIFICADOS");
							alert.setHeaderText(null);
							alert.setContentText("Datos modificados exitosamente");
							alert.showAndWait();
							log.editado(usuariologeado,"Bases para pastel",txtNombre.getText() );
							cancelar();
						}
						else{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("La informacion no se ha podido editar, por favor intentelo de nuevo");
							alert.showAndWait();
						}
				}
			}
	}
	@FXML public void clickMandarDatos(){
		clickTableView();
	}
	public void clickTableView(){
		if(tablaBases.getSelectionModel().getSelectedItem() != null){
			bases = tablaBases.getSelectionModel().getSelectedItem();
			txtNombre.setText(bases.getNombre());
			txtPrecio.setText(bases.getPrecio());
			txtExistencia.setText(bases.getExistencia());
			idbases.setText(String.valueOf(bases.getIdbases()));

			editar();

			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(false);
		}
			else{
				Alert alert = new Alert(AlertType.WARNING);
		    	alert.setTitle("Tabla de Registros");
		    	alert.setHeaderText("Tabla de Registros");
		    	alert.setContentText("Seleccione una fila con registros!");
		    	alert.showAndWait();
			}

	}

	@FXML public void clickEliminar(){ {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmar");
    	alert.setHeaderText("¿Desea eliminar el registro?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		this.bases.setIdbases(Integer.parseInt(idbases.getText()));
        	bases.eliminar();
        	log.eliminado(usuariologeado,"Bases para pastel",txtNombre.getText());
            listaBases=bases.mostrar();
    		tablaBases.setItems(bases.mostrar());
    		clickCancelar();
    	} else {
    		alert.close();
    	}
	  }
	}

	@FXML public void clickNuevo(){
		nuevo();
	}
	public void nuevo(){
		txtNombre.setDisable(false);
		txtPrecio.setDisable(false);
		txtExistencia.setDisable(false);
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
	}

	@FXML public void clickCancelar(){
		cancelar();
	}
	public void cancelar(){
		txtNombre.setDisable(true);
		txtPrecio.setDisable(true);
		txtExistencia.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEditar.setDisable(true);
		btnEliminar.setDisable(true);
		txtNombre.setText("");
		txtPrecio.setText("");
		txtExistencia.setText("");
	}

	@FXML public void clickGuardar(){
		if(txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()||txtExistencia.getText().isEmpty()){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ingrese Datos");
		alert.setHeaderText("Campos Vacios");
    	alert.setContentText("Por favor ingrese la informacion solicitada!");
    	alert.showAndWait();
		}
		else{
	        		if(txtPrecio.getLength()>7){
	        			Alert alert =new Alert(AlertType.WARNING);
				    	alert.setTitle("Datos no validos");
				    	alert.setHeaderText("Numero no cumple con requerido");
				    	alert.setContentText("Numero demaciado corto");
				    	alert.showAndWait();
	        		}
						else{
						bases.setNombre(txtNombre.getText());
						bases.setPrecio(txtPrecio.getText());
						bases.setExistencia(txtExistencia.getText());
						if(bases.insertar()==true){
	    	        		System.out.println("Se insertaron los datos correctamente");
	    	        		Alert alert = new Alert(AlertType.INFORMATION);
	    	    			alert.setTitle("Informacion Agregada");
	    	    			alert.setHeaderText(null);
	    	    			alert.setContentText("La informacion se ha guardado de forma exitosa!");
	    	    			alert.showAndWait();
	    	    			//Actualiza la tabla
	    	    			listaBases=bases.mostrar();
	    	    			tablaBases.setItems(bases.mostrar());
	    	    			clickCancelar();
	    	    			log.nuevo(usuariologeado,"Base para pastel",txtNombre.getText() );
	    	        	}
	    	        	else{
	    	        		System.out.println("Error al insertar los datos");
	    		        	}

						}
	        		}
				}


}

