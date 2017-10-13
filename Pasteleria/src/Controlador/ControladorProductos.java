package Controlador;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOCategoria;
import Modelo.DAOMarcas;
import Modelo.DAOProducto;
import Controlador.ControladorVentanas;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
//import javafx.scene.control.Alert.AlertType;


public class ControladorProductos implements Initializable{
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	private ControladorVentanas ins;
	@FXML Button btnBuscar;
	@FXML Button btnNuevo;
	@FXML Button btnGuardar;
	@FXML Button btnRefrescar;
	@FXML Button btnEliminar;
	@FXML Button btnEditar;
	@FXML Button btnCancelar;
	@FXML TextField tfCodigo;
	@FXML TextField txtBuscador;
	@FXML TextField tfNombre;
	@FXML TextField tfPrecio;
	@FXML TextField tfId;
	@FXML Label fechaI;
	@FXML Label nregistros;
	@FXML ComboBox<DAOCategoria> cbCategoria;
	@FXML ComboBox<DAOMarcas> cbMarca;
	@FXML Button btnNuevaMarca;
	@FXML TextField tfTipo;
	@FXML Button btnNuevaCategoria;
	@FXML TableView<DAOProducto> tableView;
	ObservableList<DAOProducto> listadeProductos;
	ObservableList<DAOCategoria> listaCategorias;
	ObservableList<DAOMarcas> listaMarcas;
	DAOProducto datosProducto;
	DAOCategoria datosCategoria;
	DAOMarcas datosMarca;
	private int enteroCat=0;
	private int enteroMar=0;
	public ControladorProductos() {
		ins = ControladorVentanas.getInstancia();
		datosProducto=new DAOProducto();
		datosCategoria=new DAOCategoria();
		datosMarca=new DAOMarcas();
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
	}
	@FXML public void clickNuevo(){
		nuevo();
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
	public static boolean isNumeric(String src) {
		 for(int i = 0; i<src.length(); i++)
			 if( !Character.isDigit(src.charAt(i)) )
			if(src.equals("."))
			 return false;

			 return true;
    }
	@FXML public void clickGuardar(){
		String cadena=tfPrecio.getText();
		if(tfCodigo.getText().isEmpty() || tfNombre.getText().isEmpty()||tfPrecio.getText().isEmpty()||
				tfTipo.getText().isEmpty()||cbCategoria.getSelectionModel().getSelectedItem()==null||cbMarca.getSelectionModel().getSelectedItem()==null){

		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Campos Vacios", "Por favor ingrese los datos solicitados", WARNING_ICON);

		}
		else{
			if (isNumeric(cadena) == false) {
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos no validos en 'Precio'",
						"Solo se aceptan numeros", WARNING_ICON);
                }
            else {
            	if(tfCodigo.getLength()>16){
            		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos no validos en 'Codigo'",
    						"Codigo solo acepta max. 15 caracteres", WARNING_ICON);
            	}
            	else{
            		if(tfPrecio.getLength()>4){
            			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos no validos en 'Precio'",
        						"Valor demasiado grande.", WARNING_ICON);
            		}
            		else{
            			datosProducto.setCodigo(tfCodigo.getText());
                    	datosProducto.setNombre(tfNombre.getText());
                    	datosProducto.setPrecio(tfPrecio.getText());
                    	datosProducto.setTipo(tfTipo.getText());

                    	String idc=cbCategoria.getSelectionModel().getSelectedItem().toString();
                    	enteroCat=datosCategoria.consultarIdCategoria("select id from categoria where nombre='"+idc+"'");
                    	datosProducto.setIdCategoria(enteroCat);
                    	//Para recuperar el nombre de la marca
                    	String idm=cbMarca.getSelectionModel().getSelectedItem().toString();
                    	enteroMar=datosMarca.consultarIdMarca("select id from marca where nombremarca='"+idm+"'");
                    	datosProducto.setIdMarca(enteroMar);

                    	if(datosProducto.insertar()==true){
                    		System.out.println("Se insertaron los datos correctamente");
                    		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Ingresados",
            						"Los datos se agregaron correctamente", SUCCESS_ICON);

                			listadeProductos=datosProducto.mostrar();
                			tableView.setItems(datosProducto.mostrar());
                			btnNuevo.setDisable(false);
                			btnEditar.setDisable(true);
                			btnEliminar.setDisable(true);
                			tfPrecio.setText("");
                			tfNombre.setText("");
                			tfCodigo.setText("");
                			tfTipo.setText("");
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
	}
	@FXML public void clickIrMarca(){
		ins.asignarModal("../Vistas/Marca.fxml","Marcas para productos");
		}
	@FXML public void clickIrCategoria(){
		ins.asignarModal("../Vistas/Categoria.fxml","Categorias de productos");
		}
	 private void abrir() {
		  //ruta del archivo en el pc
		  String file = new String("C:/Users/Fredy/workspace/Pasteleriasrc/Documentos/Ayuda_Producto.pdf");

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
						this.datosProducto.setPrecio(tfPrecio.getText());
						this.datosProducto.setIdProducto(Integer.parseInt(tfId.getText()));

						if(datosProducto.editar()){
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
	@FXML public void clickMandarDatos(){
		clickTableView();
	}
	public void clickTableView(){
		if(tableView.getSelectionModel().getSelectedItem() != null){
			datosProducto = tableView.getSelectionModel().getSelectedItem();
			tfNombre.setText(datosProducto.getNombre());
			tfTipo.setText(datosProducto.getTipo());
			tfPrecio.setText(datosProducto.getPrecio());
			tfCodigo.setText(datosProducto.geCodigo());
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
		        int confirmarEliminar = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar este producto??");

		        if (confirmarEliminar == 0) {
		        	this.datosProducto.setIdProducto(Integer.parseInt(tfId.getText()));
		            datosProducto.eliminar();
		            System.out.println("Realizado Eliminado");
		            listadeProductos=datosProducto.mostrar();
		    		tableView.setItems(datosProducto.mostrar());

		    }
		}
	}




