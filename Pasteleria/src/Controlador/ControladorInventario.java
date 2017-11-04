package Controlador;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Controlador.notificaciones.Notification.Notifier;
import Modelo.DAOConexion;
import Modelo.DAOProducto;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;


public class ControladorInventario implements Initializable{
	private static final Image WARNING_ICON= new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/warning.png"));
	public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/Vistas/iconos/success.png"));
	private ControladorVentanas ins;
	@FXML ComboBox<DAOProducto> cbNomProd;
	@FXML Button btnNueProd;
	@FXML Button btnNuevo,btnGuardar,btnCancelar,btnEditar;
	@FXML Label cantidadRegistros;
	@FXML Label leyenda1;
	@FXML Label leyenda2;
	@FXML TextField tfCantidad;
	@FXML TextField tfId;
	@FXML TableView<DAOProducto> tabla;
	DAOProducto datosProducto;
	ObservableList<DAOProducto> listadeProductos;
	ControladorLog log;
	ControladorAcceso nua;
	DAOConexion con;

	public ControladorInventario() {
		datosProducto=new DAOProducto();
		ins = ControladorVentanas.getInstancia();
		nua=new ControladorAcceso();
		log=new ControladorLog();
		con=new DAOConexion();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listadeProductos=datosProducto.mostrar();
		cbNomProd.setItems(listadeProductos);
		listadeProductos=datosProducto.mostrarInventario();
		tabla.setItems(datosProducto.mostrarInventario());
		int r=listadeProductos.size();
		String reg=Integer.toString(r);
		cantidadRegistros.setText(reg);
		restricciones();
	}
	public void restricciones(){
		tfCantidad.textProperty().addListener((observable, oldValue, newValue)->{
	        if(!newValue.matches("[0-9]{0,10}"))
	            ((StringProperty)observable).setValue(oldValue);
	        else
	            ((StringProperty)observable).setValue(newValue);
    	});
	}
	@FXML public void clickNuevo(){
		nuevo();
	}
	public void nuevo(){
		btnNuevo.setDisable(true);
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		tfCantidad.setDisable(false);
		cbNomProd.setDisable(false);
		btnNueProd.setDisable(false);
	}
	@FXML public void clickCancelar(){
		cancelar();
	}
	public void cancelar(){
		btnNuevo.setDisable(false);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		btnEditar.setDisable(true);
		tfCantidad.setDisable(true);
		tfCantidad.setText("1");
		btnNueProd.setDisable(true);
		cbNomProd.setDisable(true);
		leyenda1.setText("");
		leyenda2.setText("");
	}
	@FXML public void clickTableView(){
		if(tabla.getSelectionModel().getSelectedItem() != null){
			datosProducto = tabla.getSelectionModel().getSelectedItem();
			tfCantidad.setText(String.valueOf(datosProducto.getCantidad()));
			tfId.setText(String.valueOf(datosProducto.getIdProducto()));
			leyenda1.setText("Editando:"); leyenda2.setText(datosProducto.getNombre());
			btnNuevo.setDisable(true);
			btnGuardar.setDisable(true);
			cbNomProd.setDisable(true);
			tfCantidad.setDisable(false);
			btnEditar.setDisable(false);
			btnCancelar.setDisable(false);

		}
			else{
				Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Tabla de Registros",
						"Seleccione una fila con registros", WARNING_ICON);
			}

	}
	@FXML public void guardar(){
		if(cbNomProd.getSelectionModel().getSelectedItem()==null){
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify
			("Campo Vacío", "Por favor seleccione un producto de la lista", WARNING_ICON);
		}
		else{
				datosProducto.setCantidad(Integer.parseInt(tfCantidad.getText()));
				datosProducto.setNombre(cbNomProd.getSelectionModel().getSelectedItem().toString());

				if(datosProducto.insertarInventario()==true){
            		System.out.println("Se insertaron los datos correctamente");
            		Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Ingresados",
    						"Los datos se agregaron correctamente", SUCCESS_ICON);
            		log.nuevo(nua.getUsuarioActivo(),"Registro de inventario",cbNomProd.getSelectionModel().getSelectedItem().toString());

            		cancelar();
            		actualizar();

				}
				else{
					System.out.println("Se ha presentado un error al momento de insertar");
				}
			}
		}

	@FXML public void actualizar(){
		listadeProductos=datosProducto.mostrarInventario();
		tabla.setItems(datosProducto.mostrarInventario());
	}
	@FXML public void editar(){
		datosProducto.setCantidad(Integer.parseInt(tfCantidad.getText()));
		datosProducto.setIdProducto(Integer.parseInt(tfId.getText()));
		if(datosProducto.editarStock()){
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Datos Modificados",
					"La cantidad ha sido actualizada", SUCCESS_ICON);
			actualizar();
			tabla.refresh();
			//log.editado(nua.getUsuarioActivo(),"Inventario",cbNomProd.getSelectionModel().getSelectedItem().toString());
			leyenda1.setText("");
			leyenda2.setText("");
			cancelar();
		}
		else{
			Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Error",
					"La informacion no se ha podido editar, por favor intentelo de nuevo", WARNING_ICON);
		}
	}
	@FXML public void nuevoProducto(){
		ins.asignarModal("../Vistas/Productos.fxml","Productos");
	}
	@FXML public void clickPDf(){
		generarPDF();
	}

	public void generarPDF(){
		 Calendar fecha = new GregorianCalendar();
		 int año = fecha.get(Calendar.YEAR);
		 int mes = fecha.get(Calendar.MONTH)+1;
		 int dia = fecha.get(Calendar.DAY_OF_MONTH);
		 int hora = fecha.get(Calendar.HOUR_OF_DAY);
		 int minuto = fecha.get(Calendar.MINUTE);
		try{
	        ResultSet rs = null;
	        PreparedStatement comando;
			 OutputStream file = new FileOutputStream(new File("C://VAMA980429HVZZJL02//Rep_Inv"+dia+"-"+mes+"-"+año+"_"+hora+"~"+minuto+".pdf"));
		      Document document = new Document(PageSize.A4, 35, 30, 50, 50);


		      PdfWriter.getInstance(document, file);
		      document.open();

		      com.itextpdf.text.Image image=null;
		      com.itextpdf.text.Image image2=null;
		      image =com.itextpdf.text.Image.getInstance("src/Vistas/images/logopast.png");
		      image.scaleAbsolute(80f, 80f);
		      image2 = com.itextpdf.text.Image.getInstance("src/Vistas/images/RAM-SI.jpg");
		      image2.scaleAbsolute(210f, 70f);

		      PdfPTable table = new PdfPTable(2);
		      PdfPCell cell =new PdfPCell(image);
		      PdfPCell cell2 =new PdfPCell(image2);
		      table.addCell(cell);
		      table.addCell(cell2);
		      cell.setBorderColor(BaseColor.WHITE);
		      cell2.setBorderColor(BaseColor.WHITE);
		      document.add(table);



		      //Titulo y tabla de informacion
		      PdfPTable tablaI = new PdfPTable(4);
		      Paragraph pT = new Paragraph("Inventario\n\n", FontFactory.getFont("Century Gothic",26,BaseColor.DARK_GRAY));
		      pT.setAlignment(Element.ALIGN_CENTER);
		      Paragraph pS=new Paragraph("Generado:"+dia+"/"+mes+"/"+año+","+hora+":"+minuto+" hrs.", FontFactory.getFont("Century Gothic",12,BaseColor.DARK_GRAY));
		      pS.setAlignment(Element.ALIGN_CENTER);
		      document.add(pT);
		      document.add(pS);
		      document.add(new Paragraph(""));


		            float[] mediaCeldas ={3.70f,3.00f,3.00f,2.00f};

		            tablaI.setWidths(mediaCeldas);
		            tablaI.addCell(new Paragraph("Nombre", FontFactory.getFont("Century Gothic",12,Font.BOLD)));
		            tablaI.addCell(new Paragraph("Codigo", FontFactory.getFont("Century Gothic",12,Font.BOLD)));
		            tablaI.addCell(new Paragraph("Cantidad", FontFactory.getFont("Century Gothic",12,Font.BOLD)));
		            tablaI.addCell(new Paragraph("Precio", FontFactory.getFont("Century Gothic",12,Font.BOLD)));


		            if(con.conectar()) {
		            	String sql = "select * from productos where estatus='TRUE' and cantidad>0";
		                comando = con.getConexion().prepareStatement(sql);
		                rs = comando.executeQuery();
		                while(rs.next()){

		                	 tablaI.addCell(new Paragraph(rs.getString("nombre"), FontFactory.getFont("Arial",10)));
		                	 tablaI.addCell(new Paragraph(rs.getString("codigo"), FontFactory.getFont("Arial",10)));
		                	 int can=rs.getInt("cantidad");
		                	 tablaI.addCell(new Paragraph(String.valueOf(can), FontFactory.getFont("Arial",10)));
		                	 tablaI.addCell(new Paragraph("$"+rs.getString("precio"), FontFactory.getFont("Arial",10)));
		                }
		            }
		            document.add(tablaI);
		             document.close();
		             file.close();
		             Controlador.notificaciones.Notification.Notifier.INSTANCE.notify("Reporte de inventario",
	    						"El reporte se ha generado correctamente", SUCCESS_ICON);

		        }

		          catch (Exception e){
		          e.printStackTrace();
		      }
	}
}
