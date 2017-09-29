package Controlador;



import Controlador.ControladorVentanas;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

	private ControladorVentanas ventanas;
	@Override
	public void start(Stage primaryStage) {
		ventanas = ControladorVentanas.getInstancia();
		ventanas.setPrimaryStage(primaryStage);
		ventanas.asignarModal("../Vistas/LoginView.fxml","Login");
	}
	public static void main(String[] args) {
		launch(args);
	}
}