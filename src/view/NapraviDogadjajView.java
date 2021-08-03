package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Dogadjaj;
import model.Vaznosti;
import sample.Main;
import java.sql.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class NapraviDogadjajView extends GridPane {

    public static Scene makeScene(){
        return new Scene(new NapraviDogadjajView(), 250, 200);
    }

    private Label nazivDogadjajaLbl;
    private Label datumDogadjajaLbl;
    private Label vaznostDogadjajaLbl;

    private TextField nazivDogadjajaTF;

    private ComboBox<Vaznosti> vaznostDogadjajaCB;

    private DatePicker datumDogadjajaDP;

    private Button napraviDogadjajBtn;
    private Button nazadBtn;

    public NapraviDogadjajView(){
        elements();
        listeners();
    }

    private void elements() {
        nazivDogadjajaLbl = new Label("Naziv");
        datumDogadjajaLbl = new Label("Datum");
        vaznostDogadjajaLbl = new Label("Vaznost");

        nazivDogadjajaTF = new TextField();

        datumDogadjajaDP = new DatePicker();

        vaznostDogadjajaCB = new ComboBox<>();
        vaznostDogadjajaCB.getItems().addAll(Vaznosti.values());

        napraviDogadjajBtn = new Button("Napravi dogadjaj");
        nazadBtn = new Button("Nazad");

        this.add(nazivDogadjajaLbl, 0, 0);
        this.add(datumDogadjajaLbl, 0, 1);
        this.add(vaznostDogadjajaLbl, 0, 2);

        this.add(nazivDogadjajaTF, 1, 0);
        this.add(datumDogadjajaDP, 1, 1);
        this.add(vaznostDogadjajaCB, 1, 2);

        this.add(napraviDogadjajBtn, 1, 3);
        this.add(nazadBtn, 1, 4);

        this.setVgap(10);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
    }

    private void listeners() {
        napraviDogadjajBtn.setOnAction(e -> {
            try {
                //Pisanje u bazu
                String naziv = nazivDogadjajaTF.getText();
                LocalDate datum = datumDogadjajaDP.getValue();
                String vaznost = String.valueOf(vaznostDogadjajaCB.getSelectionModel().getSelectedItem());

                Connection myConnection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yoxtgC2qlb", "yoxtgC2qlb", "AncWWRgRAb");

                String myResultSet = "INSERT INTO Dogadjaji (naziv, datum, vaznost) VALUES(?, ?, ?)";

                PreparedStatement myPreparedStatement = myConnection.prepareStatement(myResultSet);
                myPreparedStatement.setString(1, naziv);
                myPreparedStatement.setDate(2, Date.valueOf(datum));
                myPreparedStatement.setString(3, vaznost);

                myPreparedStatement.execute();
                myConnection.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        nazadBtn.setOnAction(e -> {
            Main.window.setScene(PocetniView.makeScene());
        });
    }
}