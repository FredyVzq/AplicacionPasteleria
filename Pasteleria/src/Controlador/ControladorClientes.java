package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Modelo.DAOCliente;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControladorClientes implements Initializable{
	@FXML TextField txtNombre;
	@FXML TextField txtApePat;
	@FXML TextField txtApeMat;
	@FXML TextField txtCiudad;
	@FXML TextField txtCalle;
	@FXML TextField txtColonia;
	@FXML TextField txtNumCel;
	@FXML TextField txtNumCasa;
	@FXML TextField idCliente;
	@FXML Label txtRegistros;
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TableView<DAOCliente> tablaCliente;
	ObservableList<DAOCliente> listaCliente;
	DAOCliente cliente;
	@SuppressWarnings("unused")
	private ControladorVentanas ins;

	public  ControladorClientes() {
		ins = ControladorVentanas.getInstancia();
		cliente=new DAOCliente();	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaCliente=cliente.mostrar();
		tablaCliente.setItems(cliente.mostrar());
		int r=listaCliente.size();
		String reg=Integer.toString(r);
		txtRegistros.setText(reg);
}

	public void Editar(){
		btnEditar.setDisable(false);
		btnCancelar.setDisable(false);
		txtNombre.setDisable(false);
		txtApePat.setDisable(false);
		txtApeMat.setDisable(false);
		txtCiudad.setDisable(false);
		txtColonia.setDisable(false);
		txtCalle.setDisable(false);
		txtNumCel.setDisable(false);
		txtNumCasa.setDisable(false);
	}
	@FXML public void clickEditar(){
		if(txtNombre.getText().isEmpty() || txtApePat.getText().isEmpty()|| txtApeMat.getText().isEmpty() ||
				txtNumCel.getText().isEmpty()||txtColonia.getText().isEmpty()){

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("DATOS FALTANTES");
				alert.setHeaderText(null);
				alert.setContentText("Por favor llena todos los campos.");
				alert.showAndWait();
			}
			else{
				boolean confirmar2=false;
				if(confirmar2==false){
						this.cliente.setNombre(txtNombre.getText());
						this.cliente.setApPat(txtApePat.getText());
						this.cliente.setApMat(txtApeMat.getText());
						this.cliente.setCiudad(txtCiudad.getText());
						this.cliente.setColonia(txtColonia.getText());
						this.cliente.setCalle(txtCalle.getText());
						this.cliente.setNumeroCel(txtNumCel.getText());
						this.cliente.setNumeroCasa(txtNumCasa.getText());
						this.cliente.setIdCliente(Integer.parseInt(idCliente.getText()));


						if(cliente.editar()){
					    	Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Error");
					    	alert.setHeaderText("verifica los datos");
					    	alert.setContentText("verifica los datos!");
					    	alert.showAndWait();
							Editar();

						}
						else{
							Alert alert = new Alert(AlertType.INFORMATION);
					    	alert.setTitle("Datos Correctos!");
					    	alert.setHeaderText("Datos modificados");
					    	alert.setContentText("Datos modificados correctamente!");
					    	alert.showAndWait();
					    	listaCliente=cliente.mostrar();
							tablaCliente.setItems(cliente.mostrar());
							int r=listaCliente.size();
							String reg=Integer.toString(r);
							txtRegistros.setText(reg);
						}
				}
			}
	}
	@FXML public void clickActualizar(){
		listaCliente=cliente.mostrar();
		tablaCliente.setItems(cliente.mostrar());
		int r=listaCliente.size();
		String reg=Integer.toString(r);
		txtRegistros.setText(reg);
	}
	@FXML public void clickMandarDatos(){
		clickTableView();
		btnEliminar.setDisable(false);

	}
	public void clickTableView(){
		if(tablaCliente.getSelectionModel().getSelectedItem() != null){
			cliente = tablaCliente.getSelectionModel().getSelectedItem();
			idCliente.setText(String.valueOf(cliente.getIdCliente()));
			txtApePat.setText(cliente.getApPat());
			txtApeMat.setText(cliente.getApMat());
			txtCiudad.setText(cliente.getCiudad());
			txtCalle.setText(cliente.getCalle());
			txtColonia.setText(cliente.getColonia());
			txtNombre.setText(cliente.getNombre());
			txtNumCel.setText(cliente.getNumeroCel());
			txtNumCasa.setText(cliente.getNumeroCasa());
			Editar();
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
		}
			else{
				Alert alert = new Alert(AlertType.WARNING);
		    	alert.setTitle("No selecciono");
		    	alert.setHeaderText("Seleccione un registro");
		    	alert.setContentText("seleccione un registro!");
		    	alert.showAndWait();
			}

	}

	@FXML public void clickNuevo(){
		txtNombre.setDisable(false);
		txtApePat.setDisable(false);
		txtApeMat.setDisable(false);
		txtCiudad.setDisable(false);
		txtColonia.setDisable(false);
		txtCalle.setDisable(false);
		txtNumCel.setDisable(false);
		txtNumCasa.setDisable(false);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
	}

	@FXML public void clickCancelar(){
		txtNombre.setDisable(true);
		txtApePat.setDisable(true);
		txtApeMat.setDisable(true);
		txtCiudad.setDisable(true);
		txtCalle.setDisable(true);
		txtNumCel.setDisable(true);
		txtNumCasa.setDisable(true);
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		txtColonia.setDisable(true);
		txtNombre.clear();
		txtApePat.clear();
		txtApeMat.clear();
		txtCiudad.clear();
		txtColonia.clear();
		txtCalle.clear();
		txtNumCel.clear();
		txtNumCasa.clear();
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
		String cadena=txtNumCel.getText();
		String cad=txtNumCasa.getText();
		if(txtNombre.getText().isEmpty() || txtApePat.getText().isEmpty()|| txtApeMat.getText().isEmpty() ||
				txtNumCel.getText().isEmpty()||txtColonia.getText().isEmpty()){

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
	        		if(txtNumCel.getLength()<10){
	        			Alert alert = new Alert(AlertType.WARNING);
				    	alert.setTitle("Datos no validos");
				    	alert.setHeaderText("Numero no cumple con requerido");
				    	alert.setContentText("Numero demaciado corto");
				    	alert.showAndWait();
	        		}
	        		else{
	            		if(txtNumCasa.getLength()<0){
	            			Alert alert = new Alert(AlertType.WARNING);
					    	alert.setTitle("Datos no validos");
					    	alert.setHeaderText("Numero no cumple con requerido");
					    	alert.setContentText("Numero demaciado corto");
					    	alert.showAndWait();
	            		}



						else{
						cliente.setNombre(txtNombre.getText());
						cliente.setApPat(txtApePat.getText());
						cliente.setApMat(txtApeMat.getText());
						cliente.setCiudad(txtCiudad.getText());
						cliente.setColonia(txtColonia.getText());
						cliente.setCalle(txtCalle.getText());
						cliente.setNumeroCel(txtNumCel.getText());
						cliente.setNumeroCasa(txtNumCasa.getText());
						if(cliente.insertar()==true){
	    	        		System.out.println("Se insertaron los datos correctamente");
	    	        		Alert alert = new Alert(AlertType.INFORMATION);
	    	    			alert.setTitle("Informacion Agregada");
	    	    			alert.setHeaderText(null);
	    	    			alert.setContentText("La informacion se ha guardado de forma exitosa!");
	    	    			alert.showAndWait();
	    	    			//Actualiza la tabla
	    	    			listaCliente=cliente.mostrar();
	    	    			tablaCliente.setItems(cliente.mostrar());
	    	    			int r=listaCliente.size();
	    	    			String reg=Integer.toString(r);
	    	    			txtRegistros.setText(reg);
	    	    			btnGuardar.setDisable(true);
	    	    			btnNuevo.setDisable(false);
	    	    			txtNombre.clear();txtNombre.setDisable(true);
	    	    			txtApePat.clear();txtApePat.setDisable(true);
	    	    			txtApeMat.clear();txtApeMat.setDisable(true);
	    	    			txtCiudad.clear();txtCiudad.setDisable(true);
	    	    			txtColonia.clear();txtColonia.setDisable(true);
	    	    			txtCalle.clear();txtCalle.setDisable(true);
	    	    			txtNumCel.clear();txtNumCel.setDisable(true);
	    	    			txtNumCasa.clear();txtNumCasa.setDisable(true);
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
	@FXML public void clickEliminar(){
		int confirmarEliminar = JOptionPane.showConfirmDialog(null, "¿Realmente desea eliminar este registro de cliente?");

        if (confirmarEliminar == 0) {
        	this.cliente.setIdCliente(Integer.parseInt(idCliente.getText()));
        	cliente.eliminar();
            System.out.println("Realizado Eliminado");
            clickActualizar();
        	txtNombre.clear();txtNombre.setDisable(true);
			txtApePat.clear();txtApePat.setDisable(true);
			txtApeMat.clear();txtApeMat.setDisable(true);
			txtCiudad.clear();txtCiudad.setDisable(true);
			txtColonia.clear();txtColonia.setDisable(true);
			txtCalle.clear();txtCalle.setDisable(true);
			txtNumCel.clear();txtNumCel.setDisable(true);
			txtNumCasa.clear();txtNumCasa.setDisable(true);
        }
	}
}



