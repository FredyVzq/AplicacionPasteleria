package Controlador;

import java.net.URL;
import java.util.ResourceBundle;

import Modelo.DAOConexion;
import Modelo.DAOProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControladorVentas implements Initializable {
	DAOConexion con;
	@FXML Button btnBuscar;
	@FXML TextField codigo;
	@FXML TextField descripcion;
	@FXML TextField cantidad;
	@FXML TableView <DAOProducto> tabla;
    private ObservableList<DAOProducto> detalle;


	public ControladorVentas(){
		con = new DAOConexion();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		con.conectar();
		detalle= FXCollections.observableArrayList();
	}

	@FXML public void busqueda(){
		DAOProducto prod;
		prod = new DAOProducto();
	//	String ol = prod.consultar(codigo.getText());
	//	if(prod.getExiste()){
	//		prod.setCantidad(Integer.parseInt(cantidad.getText()));
			detalle.add(prod);
			tabla.setItems(detalle);
			tabla.refresh();
		}
	}




