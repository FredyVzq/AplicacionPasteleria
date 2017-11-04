package Controlador;

import java.io.File;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Modelo.DAOUsuario;

public class ControladorVentanas {
    private static ControladorVentanas instancia;
    private Stage primaryStage, escenario2, taskUpdateStage;
    private Scene escena;
    private AnchorPane contenedorMenu, subcontenedor;
    private ProgressIndicator progress;

    //Constructor privado
    public ControladorVentanas() {
    	progress= new ProgressIndicator();
    }

    //Patrón Singleton
    public static ControladorVentanas getInstancia() {
        if (instancia == null)
            instancia = new ControladorVentanas();
        return instancia;
    }

    //Asignar el escenario principal
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void asignarMenu(String ruta, String titulo, DAOUsuario usuario) {
        try {
            primaryStage.setUserData(usuario);
            FXMLLoader parent = new FXMLLoader(getClass().getResource(ruta));
            contenedorMenu = (AnchorPane) parent.load();
            escena = new Scene(contenedorMenu);
            primaryStage.setScene(escena);
            primaryStage.setTitle(titulo);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            String url;
            File file = new File("src/controlador/logopast.png");
			url = file.toURI().toURL().toString();
			primaryStage.getIcons().add(new Image(url));
            //primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Asignar vistas como modal
    public void asignarModal(String ruta, String titulo) {
        try {
            FXMLLoader interfaz = new FXMLLoader(getClass().getResource(ruta));
            subcontenedor = (AnchorPane) interfaz.load();
            escenario2 = new Stage(); //Se crea un nuevo escenario
            escena = new Scene(subcontenedor);
            escenario2.setScene(escena);
            escenario2.setTitle(titulo);
            escenario2.resizableProperty().setValue(Boolean.FALSE);
            escenario2.centerOnScreen();
            escenario2.initModality(Modality.APPLICATION_MODAL); //Comportamiento modal
            escenario2.initOwner(primaryStage); //Para asignar al propietario
            //escenario2.show();
            cargando();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void inicio(String titulo) {
        try {
            FXMLLoader interfaz = new FXMLLoader(getClass().getResource("../Vistas/LoginView.fxml"));
            subcontenedor = (AnchorPane) interfaz.load();
            escenario2 = new Stage(); //Se crea un nuevo escenario
            escena = new Scene(subcontenedor);
            escenario2.setScene(escena);
            escenario2.setTitle(titulo);
            escenario2.resizableProperty().setValue(Boolean.FALSE);
            escenario2.centerOnScreen();
            escenario2.initModality(Modality.APPLICATION_MODAL); //Comportamiento modal
            escenario2.initOwner(primaryStage); //Para asignar al propietario
            escenario2.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    public void cerrarAcceso(){
        escenario2.close();
    }


    public void cargando(){
        VBox updatePane = new VBox();
        updatePane.setPadding(new Insets(10));
        updatePane.setSpacing(5.0d);
        updatePane.getChildren().add(progress);
      	updatePane.setStyle("-fx-background-color: rgba(147, 46, 115, 0.9);" +
                         "-fx-background-insets: 50;");
        taskUpdateStage = new Stage(StageStyle.TRANSPARENT);
        escena = new Scene(updatePane);
        escena.setFill(Color.TRANSPARENT);
        taskUpdateStage.setScene(escena);
        taskUpdateStage.centerOnScreen();
        taskUpdateStage.show();
        Task<Void> tareas = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max=10;
                for(int i=1; i<=max; i++){
                    if(isCancelled()){
                        break;
                    }
                    updateProgress(i,max);
                    updateMessage("Cargando " + String.valueOf(i));
                    Thread.sleep(100);
                }
                return null;
            }
        } ;

        tareas.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                taskUpdateStage.hide();
                escenario2.show();
            }
        });
        progress.progressProperty().bind(tareas.progressProperty());
        //lblMensaje.textProperty().bind(tareas.messageProperty());
        taskUpdateStage.show();
        new Thread(tareas).start();
    }
}