package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Database;
import model.Dogadjaj;
import model.Vaznosti;
import sample.Main;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class PregledDogadjajaView extends HBox {

    public static Scene makeScene(){
        return new Scene(new PregledDogadjajaView(), 500,300);
    }

    private ListView<String> dogadjajiLV;

    private Label nazivDogadjajaLbl;
    private Label datumDogadjajaLbl;
    private Label vaznostDogadjajaLbl;

    private VBox dogadjajVB;

    private Button nazadBtn;
    private Button izbrisiBtn;
    private Button urediBtn;

    private Dogadjaj dogadjaj = null;

    public PregledDogadjajaView(){
        elements();
        listeners();
    }

    private void elements() {
        dogadjajiLV = new ListView<>();
        for(Dogadjaj d : Database.getInstance().getDogadjajiLista()){
            dogadjajiLV.getItems().addAll(d.getNazivDogadjaja());
        }

        nazivDogadjajaLbl = new Label();
        datumDogadjajaLbl = new Label();
        vaznostDogadjajaLbl = new Label();

        //vbox za dogadjaje
        dogadjajVB = new VBox();
        dogadjajVB.getChildren().addAll(nazivDogadjajaLbl, datumDogadjajaLbl, vaznostDogadjajaLbl);
        dogadjajVB.setSpacing(10);
        dogadjajVB.setPadding(new Insets(10));

        nazadBtn = new Button("Nazad");
        izbrisiBtn = new Button("Izbrisi dogadjaj");
        urediBtn = new Button("Uredi dogadjaj");

        HBox dugmadHB = new HBox();
        dugmadHB.getChildren().addAll(izbrisiBtn, urediBtn, nazadBtn);
        dugmadHB.setSpacing(10);

        //vbox za listu i dugme
        VBox vBox = new VBox();
        vBox.getChildren().addAll(dogadjajiLV, dugmadHB);
        vBox.setSpacing(10);

        this.getChildren().addAll(vBox, dogadjajVB);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
    }

    private void listeners() {
        dogadjajiLV.setOnMouseClicked(e ->{
            String vazno = "";
            String dogadjaj = dogadjajiLV.getSelectionModel().getSelectedItem();

            //pretraga dogadjaja
            for(Dogadjaj d : Database.getInstance().getDogadjajiLista()){
                if(d.getNazivDogadjaja().equals(dogadjaj)){
                    nazivDogadjajaLbl.setText(d.getNazivDogadjaja());
                    datumDogadjajaLbl.setText(d.getDatumDogadjaja().toString());
                    vaznostDogadjajaLbl.setText(d.getVaznostDogadjaja());
                    vazno = d.getVaznostDogadjaja();
                }
            }

            //layout za nevazne dogadjaje
            String nevaznoLayout = "-fx-border-color: blue;\n" +
                    "-fx-border-insets: 1;\n" +
                    "-fx-border-width: 1;\n" +
                    "-fx-border-radius: 5;\n";

            //layout za vazne dogadjaje
            String vaznoLayout = "-fx-border-color: red;\n" +
                    "-fx-border-insets: 1;\n" +
                    "-fx-border-width: 1;\n" +
                    "-fx-border-radius: 5;\n";

            if(vazno.equals("VAZNO")) dogadjajVB.setStyle(vaznoLayout);
            else dogadjajVB.setStyle(nevaznoLayout);
        });

        izbrisiBtn.setOnAction(e -> {
            String dogadjaj = dogadjajiLV.getSelectionModel().getSelectedItem();
            Dogadjaj dog = null;

            for(Dogadjaj d : Database.getInstance().getDogadjajiLista()){
                if(d.getNazivDogadjaja().equals(dogadjaj)){
                     dog = d;

                    try {
                        Connection myConnection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yoxtgC2qlb", "yoxtgC2qlb", "AncWWRgRAb");

                        String query = "delete from Dogadjaji where naziv = ?";
                        PreparedStatement myPreparedStatement = myConnection.prepareStatement(query);

                        myPreparedStatement.setString(1, d.getNazivDogadjaja());

                        myPreparedStatement.execute();
                        myConnection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            Database.getInstance().getDogadjajiLista().remove(dog);
            dogadjajiLV.getItems().remove(dogadjaj);
            dogadjajiLV.refresh();
        });

        urediBtn.setOnAction(e -> {
            String dog = dogadjajiLV.getSelectionModel().getSelectedItem();
            for(Dogadjaj d : Database.getInstance().getDogadjajiLista()){
                if(d.getNazivDogadjaja().equals(dog)){
                    dogadjaj = d;
                }
            }
            Stage prozor = new Stage();
            prozor.setTitle("Uredi dogadjaj: " + dog);
            prozor.setScene(UrediButtonView.makeScene(this));
            prozor.show();
        });

        nazadBtn.setOnAction(e -> {
            Main.window.setScene(PocetniView.makeScene());
        });
    }

    public Dogadjaj getDogadjaj() {
        return dogadjaj;
    }

    public ListView<String> getDogadjajiLV() {
        return dogadjajiLV;
    }

    public void setDogadjajiLV(ListView<String> dogadjajiLV) {
        this.dogadjajiLV = dogadjajiLV;
    }

    public Label getNazivDogadjajaLbl() {
        return nazivDogadjajaLbl;
    }

    public void setNazivDogadjajaLbl(Label nazivDogadjajaLbl) {
        this.nazivDogadjajaLbl = nazivDogadjajaLbl;
    }

    public Label getDatumDogadjajaLbl() {
        return datumDogadjajaLbl;
    }

    public void setDatumDogadjajaLbl(Label datumDogadjajaLbl) {
        this.datumDogadjajaLbl = datumDogadjajaLbl;
    }

    public Label getVaznostDogadjajaLbl() {
        return vaznostDogadjajaLbl;
    }

    public void setVaznostDogadjajaLbl(Label vaznostDogadjajaLbl) {
        this.vaznostDogadjajaLbl = vaznostDogadjajaLbl;
    }
}
