package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import Modelo.DAOBases;


public class ControladorBases implements Initializable{
	@FXML TextField txtNombre;
	@FXML TextField txtPrecio;
	@FXML TextField txtExistencia;
	@FXML TextField txtIdbases;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOBases> tablaBases;
	ObservableList<DAOBases> listaBases;
	DAOBases bases;
	private ControladorVentanas ins;

	public  ControladorBases() {
		ins = ControladorVentanas.getInstancia();
		bases=new DAOBases();
		}

	public void initialize(URL location, ResourceBundle resources) {
		listaBases=bases.mostrar();
		tablaBases.setItems(bases.mostrar());
	}

//-------------------------
	@FXML public void editar(){
		editar();
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
				alert.setTitle("DATOS FALTANTES");
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
						this.bases.setIdbases(Integer.parseInt(txtIdbases.getText()));

						if(bases.editar()){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("DATOS MODIFICADOS");
							alert.setHeaderText(null);
							alert.setContentText("Datos modificados exitosamente");
							alert.showAndWait();

							editar();
							listaBases=bases.mostrar();
							tablaBases.setItems(bases.mostrar());

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

	@FXML public void clickEliminar(){ {
        int confirmarEliminar = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar esta base??");

        if (confirmarEliminar == 0) {
        	this.bases.setIdbases(Integer.parseInt(txtIdbases.getText()));
        	bases.eliminar();
            System.out.println("Realizado Eliminado");
            listaBases=bases.mostrar();
    		tablaBases.setItems(bases.mostrar());

    }
}
}
	@FXML public void clickMandarDatos(){
		clickTableView();
		btnEliminar.setDisable(false);
		btnEditar.setDisable(false);
	}
	public void clickTableView(){
		if(tablaBases.getSelectionModel().getSelectedItem() != null){
			bases = tablaBases.getSelectionModel().getSelectedItem();
			txtNombre.setText(bases.getNombre());
			txtPrecio.setText(bases.getPrecio());
			txtExistencia.setText(bases.getExistencia());


			editar();
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(true);
		}
			else{
				Alert alert = new Alert(AlertType.WARNING);
		    	alert.setTitle("Tabla de Registros");
		    	alert.setHeaderText("Tabla de Registros");
		    	alert.setContentText("Seleccione una fila con registros!");
		    	alert.showAndWait();
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
		btnEditar.setDisable(false);
		btnEliminar.setDisable(false);
		txtNombre.setText("");
		txtPrecio.setText("");
		txtExistencia.setText("");
	}
public static boolean numeric(String src) {
	 for(int i = 0; i<src.length(); i++)
		 if( !Character.isDigit(src.charAt(i)) )
		 {
			 return false;
		 }

		 return true;

}
	@FXML public void clickGuardar(){
		String cadena=txtPrecio.getText();
		String cad=txtExistencia.getText();
		if(txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()||txtExistencia.getText().isEmpty()){

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ingrese Datos");
		alert.setHeaderText("Campos Vacios");
    	alert.setContentText("Por favor ingrese la informacion solicitada!");
    	alert.showAndWait();

		}
		else{
			if (numeric(cadena) == false) {
				Alert alert = new Alert(AlertType.WARNING);
		    	alert.setTitle("Datos no validos");
		    	alert.setHeaderText("No se admiten letras");
		    	alert.setContentText("Numero de celular contiene letras");
		    	alert.showAndWait();
                }
			else{
				if(numeric(cad) == false)
				{
					Alert alert = new Alert(AlertType.WARNING);
			    	alert.setTitle("Datos no validos");
			    	alert.setHeaderText("No se admiten letras");
			    	alert.setContentText("Numero de casa contiene letras");
			    	alert.showAndWait();
				}
				else{
	        		if(txtPrecio.getLength()>4){
	        			Alert alert = new Alert(AlertType.WARNING);
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
	    	        	}
	    	        	else{
	    	        		System.out.println("Error al insertar los datos");
	    		        	}

						}

	        		}

			}

		  }

	  }
	}
