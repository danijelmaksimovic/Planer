package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Database;
import model.Dogadjaj;
import model.Vaznosti;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UrediButtonView extends GridPane {

    PregledDogadjajaView pdv;

    public static Scene makeScene(PregledDogadjajaView pregledDogadjajaView){
        return new Scene(new UrediButtonView(pregledDogadjajaView), 300, 300);
    }

    private String naziv;
    private Date datum;
    private String vaznost;

    private Label stariNaziv;
    private Label stariNazivVrednost;
    private Label noviNaziv;
    private Label stariDatum;
    private Label stariDatumVrednost;
    private Label noviDatum;
    private Label staraVaznost;
    private Label staraVaznostVrednost;
    private Label novaVaznost;

    private TextField noviNazivTF;
    private DatePicker noviDatumDP;
    private ComboBox<Vaznosti> novaVaznostCB;

    private Button urediBtn;

    public UrediButtonView(PregledDogadjajaView pregledDogadjajaView){
        naziv = "";
        datum = null;
        vaznost = "";
        pdv = pregledDogadjajaView;
        elements();
        listeners();
    }

    private void elements() {
        stariNaziv = new Label("Stari naziv: ");
        stariNazivVrednost = new Label(pdv.getDogadjaj().getNazivDogadjaja());
        noviNaziv = new Label("Novi naziv");
        stariDatum = new Label("Stari datum: ");
        stariDatumVrednost = new Label(pdv.getDogadjaj().getDatumDogadjaja().toString());
        noviDatum = new Label("Novi datum");
        staraVaznost = new Label("Stara vaznost: ");
        staraVaznostVrednost = new Label(pdv.getDogadjaj().getVaznostDogadjaja());
        novaVaznost = new Label("Nova vaznost");

        noviNazivTF = new TextField();

        noviDatumDP = new DatePicker();

        novaVaznostCB = new ComboBox<>();
        novaVaznostCB.getItems().addAll(Vaznosti.values());

        urediBtn = new Button("Uredi");

        this.add(stariNaziv, 0, 0);
        this.add(stariNazivVrednost, 1, 0);
        this.add(noviNaziv, 0, 1);
        this.add(noviNazivTF, 1, 1);
        this.add(stariDatum, 0, 2);
        this.add(stariDatumVrednost, 1, 2);
        this.add(noviDatum, 0, 3);
        this.add(noviDatumDP, 1, 3);
        this.add(staraVaznost, 0, 4);
        this.add(staraVaznostVrednost, 1, 4);
        this.add(novaVaznost, 0, 5);
        this.add(novaVaznostCB, 1, 5);
        this.add(urediBtn, 1, 6);

        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));
    }

    private void listeners() {
        urediBtn.setOnAction(e -> {
            try {
                Connection myConnection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yoxtgC2qlb", "yoxtgC2qlb", "AncWWRgRAb");

                String query = "update Dogadjaji set naziv = ?, datum = ?, vaznost = ? where id = ?";
                PreparedStatement myPreparedStatement = myConnection.prepareStatement(query);

                if(noviNazivTF.getText().isEmpty()){
                    myPreparedStatement.setString(1, pdv.getDogadjaj().getNazivDogadjaja());
                    pdv.getNazivDogadjajaLbl().setText(pdv.getDogadjaj().getNazivDogadjaja());
                    naziv = pdv.getDogadjaj().getNazivDogadjaja();
                }
                else {
                    myPreparedStatement.setString(1, noviNazivTF.getText());
                    pdv.getNazivDogadjajaLbl().setText(noviNazivTF.getText());
                    naziv = noviNazivTF.getText();
                }

                if(noviDatumDP.getValue() == null){
                    myPreparedStatement.setDate(2, pdv.getDogadjaj().getDatumDogadjaja());
                    pdv.getDatumDogadjajaLbl().setText(String.valueOf(pdv.getDogadjaj().getDatumDogadjaja()));
                    datum = pdv.getDogadjaj().getDatumDogadjaja();
                }
                else {
                    myPreparedStatement.setDate(2, Date.valueOf(noviDatumDP.getValue()));
                    pdv.getDatumDogadjajaLbl().setText(String.valueOf(noviDatumDP.getValue()));
                    datum = Date.valueOf(noviDatumDP.getValue());
                }

                if(novaVaznostCB.getSelectionModel().isEmpty()){
                    myPreparedStatement.setString(3, pdv.getDogadjaj().getVaznostDogadjaja());
                    pdv.getVaznostDogadjajaLbl().setText(pdv.getDogadjaj().getVaznostDogadjaja());
                    vaznost = pdv.getDogadjaj().getVaznostDogadjaja();
                }
                else {
                    myPreparedStatement.setString(3, String.valueOf(novaVaznostCB.getSelectionModel().getSelectedItem()));
                    pdv.getVaznostDogadjajaLbl().setText(String.valueOf(novaVaznostCB.getSelectionModel().getSelectedItem()));
                    vaznost = String.valueOf(novaVaznostCB.getSelectionModel().getSelectedItem());
                }

                myPreparedStatement.setInt(4, pdv.getDogadjaj().getId());

                myPreparedStatement.execute();
                myConnection.close();

                pdv.getDogadjajiLV().getItems().clear();
                for(Dogadjaj d : Database.getInstance().getDogadjajiLista()){
                    if(d.getNazivDogadjaja().equals(pdv.getDogadjaj().getNazivDogadjaja())){
                        d.setNazivDogadjaja(naziv);
                        d.setDatumDogadjaja(datum);
                        d.setVaznostDogadjaja(vaznost);
                    }
                    pdv.getDogadjajiLV().getItems().addAll(d.getNazivDogadjaja());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    public String getNaziv() {
        return naziv;
    }

    public Date getDatum() {
        return datum;
    }

    public String getVaznost() {
        return vaznost;
    }
}
