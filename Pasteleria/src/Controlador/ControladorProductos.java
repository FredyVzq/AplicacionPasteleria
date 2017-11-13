package Controlador;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOCategoria;
import Modelo.DAOMarcas;
import Modelo.DAOProducto;
import Modelo.DAOUsuario;
import Controlador.ControladorVentanas;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
//import javafx.scene.control.Alert.AlertType;


public class ControladorProductos implements Initializable{
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	private ControladorVentanas ins;
	@FXML Button btnBuscar,btnNuevo,btnGuardar,btnRefrescar,btnEliminar,btnEditar,btnCancelar,btnNuevaMarca,btnNuevaCategoria;
	@FXML TextField tfCodigo,txtBuscador,tfNombre,tfPrecio,tfId,cantidad,tfTipo;
	@FXML RadioButton seleccion;
	@FXML Label fechaI, nregistros;
	@FXML ComboBox<DAOCategoria> cbCategoria;
	@FXML ComboBox<DAOMarcas> cbMarca;
	@FXML TableView<DAOProducto> tableView;
	ObservableList<DAOProducto> listadeProductos;
	ObservableList<DAOCategoria> listaCategorias;
	ObservableList<DAOMarcas> listaMarcas;
	private DAOProducto datosProducto;
	DAOCategoria datosCategoria;
	DAOMarcas datosMarca;
	DAOUsuario usuario;
	private int enteroCat=0;
	private int enteroMar=0;
	ControladorLog log;
	ControladorAcceso nua;
	public ControladorProductos() {
		ins = ControladorVentanas.getInstancia();
		datosProducto=new DAOProducto();
		datosCategoria=new DAOCategoria();
		datosMarca=new DAOMarcas();
		log=new ControladorLog();
		nua=new ControladorAcceso();
		usuario=new DAOUsuario();
		}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaMarcas=datosMarca.mostrar();
		cbMarca.setItems(listaMarcas);
		listaCategorias=datosCategoria.mostrar();
		cbCategoria.setItems(listaCategorias);
		listadeProductos=datosProducto.mostrar();
		tableView.setItems(datosProducto.mostrar());
		btnEliminar.setDisable(true);
		int r=listadeProductos.size();
		String reg=Integer.toString(r);
		nregistros.setText(reg);
		//Asignando la longitud de los TEXTFIELD
		restricciones();
	}
	public void restricciones(){
		tfNombre.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,50}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		tfCodigo.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,25}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		tfPrecio.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9.]{0,7}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		cantidad.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,5}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
		tfTipo.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[a-zA-Z0-9' ']{0,25}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
	}
	@FXML public void clickNuevo(){
		nuevo();
	}
	@FXML public void seleccion(){
		cantidad.setDisable(false);
	}
	public void nuevo(){
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		tfTipo.setDisable(false);
		tfCodigo.setDisable(false);
		tfNombre.setDisable(false);
		tfPrecio.setDisable(false);
		cbCategoria.setDisable(false);
		cbMarca.setDisable(false);
		btnNuevaMarca.setDisable(false);
		btnNuevaCategoria.setDisable(false);
	}
	@FXML public void clickCancelar(){
		cancelar();
	}
	public void cancelar(){
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		tfTipo.setDisable(true);
		tfCodigo.setDisable(true);
		tfNombre.setDisable(true);
		tfPrecio.setDisable(true);
		cbCategoria.setDisable(true);
		cbMarca.setDisable(true);
		btnNuevaMarca.setDisable(true);
		tfTipo.setText("");
		tfCodigo.setText("");
		tfNombre.setText("");
		btnNuevaCategoria.setDisable(false);
		tfPrecio.setText("");
		btnEditar.setDisable(true);
	}

	@FXML public void clickGuardar(){
		if(tfCodigo.getText().isEmpty() || tfNombre.getText().isEmpty()||tfPrecio.getText().isEmpty()||
				tfTipo.getText().isEmpty()||cbCategoria.getSelectionModel().getSelectedItem()==null||cbMarca.getSelectionModel().getSelectedItem()==null){

		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Campos Vacios", "Por favor ingrese los datos solicitados", WARNING_ICON);

		}
		else{
            	if(tfCodigo.getLength()>16){
            		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos no validos en 'Codigo'",
    						"Codigo solo acepta max. 15 caracteres", WARNING_ICON);
            	}
            	else{
            		if(tfPrecio.getLength()>7){
            			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos no validos en 'Precio'",
        						"Valor demasiado grande.", WARNING_ICON);
            		}
            		else{
            			datosProducto.setCodigo(tfCodigo.getText());
                    	datosProducto.setNombre(tfNombre.getText());
                    	datosProducto.setPrecio(Double.parseDouble(tfPrecio.getText()));
                    	datosProducto.setCantidad(Integer.parseInt(cantidad.getText()));
                    	datosProducto.setTipo(tfTipo.getText());

                    	String idc=cbCategoria.getSelectionModel().getSelectedItem().toString();
                    	enteroCat=datosCategoria.consultarIdCategoria("select id from categoria where nombre='"+idc+"'");
                    	datosProducto.setIdCategoria(enteroCat);
                    	//Para recuperar el nombre de la marca
                    	String idm=cbMarca.getSelectionModel().getSelectedItem().toString();
                    	enteroMar=datosMarca.consultarIdMarca("select id from marca where nombremarca='"+idm+"'");
                    	datosProducto.setIdMarca(enteroMar);

                    	if(datosProducto.insertar()==true){
                    		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Ingresados",
            						"Los datos se agregaron correctamente", SUCCESS_ICON);

                    		log.nuevo(nua.getUsuarioActivo(),"Producto",tfNombre.getText());
                			listadeProductos=datosProducto.mostrar();
                			tableView.setItems(datosProducto.mostrar());
                			cancelar();
                			cbMarca.setItems(null);
                			cbCategoria.setItems(null);
                			int r=listadeProductos.size();
                			String reg=Integer.toString(r);
                			nregistros.setText(reg);
                    	}

                    	else{
                    		System.out.println("Error al insertar los datos");
                    	}
            		}
            	}
            }
		}

	@FXML public void clickIrMarca(){
		ins.asignarModal("/Vistas/Marca.fxml","Marcas para productos");
		}
	@FXML public void clickIrCategoria(){
		ins.asignarModal("/Vistas/Categoria.fxml","Categorias de productos");
		}
	 private void abrir() {
		  //ruta del archivo en el pc
		  String file = new String("C:/Users/Fredy/workspace/Pasteleria/src/Documentos/Ayuda_Producto.pdf");

		 try{
		   //definiendo la ruta en la propiedad file
		   Runtime.getRuntime().exec("cmd /c start "+file);

		   }catch(IOException e){
		      e.printStackTrace();
		   }
		  }
	@FXML public void ayuda(){
		abrir();
	}


	@FXML public void actualizar(){
		listaMarcas=datosMarca.mostrar();
		cbMarca.setItems(listaMarcas);
		listaCategorias=datosCategoria.mostrar();
		cbCategoria.setItems(listaCategorias);
		listadeProductos=datosProducto.mostrar();
		tableView.setItems(datosProducto.mostrar());
		int r=listadeProductos.size();
		String reg=Integer.toString(r);
		nregistros.setText(reg);
	}

	@FXML public void editar(){
		btnEditar.setDisable(false);
		btnCancelar.setDisable(false);
		tfTipo.setDisable(false);
		tfCodigo.setDisable(false);
		tfNombre.setDisable(false);
		tfPrecio.setDisable(false);
		cbCategoria.setDisable(false);
		cbMarca.setDisable(false);
		btnNuevaMarca.setDisable(false);
		btnNuevaCategoria.setDisable(false);
	}
	@FXML public void clickEditar(){
		if(tfNombre.getText().trim().isEmpty() || tfCodigo.getText().trim().isEmpty()
				||tfPrecio.getText().trim().isEmpty()||tfPrecio.getText().trim().isEmpty()
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
						this.datosProducto.setNombre(tfNombre.getText());
						this.datosProducto.setCodigo(tfCodigo.getText());
						this.datosProducto.setTipo(tfTipo.getText());
						this.datosProducto.setPrecio(Double.parseDouble(tfPrecio.getText()));
						this.datosProducto.setIdProducto(Integer.parseInt(tfId.getText()));

						if(datosProducto.editar()){
							log.editado(nua.getUsuarioActivo(),"Producto",tfNombre.getText() );
							Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Modificados",
		    						"Los datos se han modificado de manera exitosa", SUCCESS_ICON);

							editar();
							listadeProductos=datosProducto.mostrar();
							tableView.setItems(datosProducto.mostrar());
							tfPrecio.setText("");
                			tfNombre.setText("");
                			tfCodigo.setText("");
                			tfTipo.setText("");
                			cbMarca.setItems(null);
                			cbCategoria.setItems(null);

						}
						else{
							Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Error",
									"La informacion no se ha podido editar, por favor intentelo de nuevo", WARNING_ICON);
						}
				}
			}
	}
	@FXML public void clickTableView(){
		if(tableView.getSelectionModel().getSelectedItem() != null){
			datosProducto = tableView.getSelectionModel().getSelectedItem();
			tfNombre.setText(datosProducto.getNombre());
			tfTipo.setText(datosProducto.getTipo());
			tfPrecio.setText(String.valueOf(datosProducto.getPrecio()));
			tfCodigo.setText(datosProducto.getCodigo());
			tfId.setText(String.valueOf(datosProducto.getIdProducto()));
			editar();
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			btnEliminar.setDisable(false);
		}
			else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Tabla de Registros",
						"Seleccione una fila con registros", WARNING_ICON);
			}

	}
	@FXML public void clickEliminar(){
		Confirmacion();
	}
	public void Confirmacion(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
//    	alert.setTitle("Favor de confirmar la eliminación del producto");
	alert.setHeaderText("Confirmar");
    	alert.setContentText("¿Desea eliminar el registro del producto?");
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		datosProducto.setIdProducto(Integer.parseInt(tfId.getText()));
	    	datosProducto.eliminar();
	        System.out.println("Realizado Eliminado");
	    	log.eliminado(nua.getUsuarioActivo(),"Producto",tfNombre.getText());
	    	cancelar();
	    	btnEliminar.setDisable(true);
	    	actualizar();
    	} else {
    		alert.close();
    	}
	}
}




