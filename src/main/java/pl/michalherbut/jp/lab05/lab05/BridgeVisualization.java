package pl.michalherbut.jp.lab05.lab05;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BridgeVisualization extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BridgeSimulator.class.getResource("bridge-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1350, 300);
        stage.setTitle("Symulator mostu!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();
    }
}