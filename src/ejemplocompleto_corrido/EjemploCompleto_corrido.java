
package ejemplocompleto_corrido;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EjemploCompleto_corrido extends Application {

      @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/formulario/PanelFX.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("formulario");
        stage.setScene(scene);
        stage.show();
    }
     
    public static void main(String[] args) {
          launch(args);
    }
    
}
