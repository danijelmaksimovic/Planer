package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Main;

public class PocetniView extends VBox {

    public static Scene makeScene(){
        return new Scene(new PocetniView(), 500, 500);
    }

    private Label naslovLbl;

    private Button napraviNoviDogadjajBtn;
    private Button pregledDogadjajaBtn;

    public PocetniView(){
        elements();
        listeners();
    }

    private void elements() {
        naslovLbl = new Label("Planer");

        naslovLbl.setStyle("-fx-font: 40 arial;");

        napraviNoviDogadjajBtn = new Button("Napravi dogadjaj");
        pregledDogadjajaBtn = new Button("Pregled dogadjaja");

        //HBox za button-e
        HBox hBox = new HBox();
        hBox.getChildren().addAll(napraviNoviDogadjajBtn, pregledDogadjajaBtn);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(naslovLbl, hBox);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

    private void listeners() {
        napraviNoviDogadjajBtn.setOnAction(e -> Main.window.setScene(NapraviDogadjajView.makeScene()));

        pregledDogadjajaBtn.setOnAction(e -> Main.window.setScene(PregledDogadjajaView.makeScene()));
    }

}
