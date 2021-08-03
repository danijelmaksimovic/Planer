package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.PocetniView;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Planer");
        window.setScene(PocetniView.makeScene());
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
