package model;

import java.sql.Date;
import java.time.LocalDate;

public class Dogadjaj {

    private int id;
    private String nazivDogadjaja;
    private Date datumDogadjaja;
    private String vaznostDogadjaja;

    public Dogadjaj(int id, String nazivDogadjaja, Date datumDogadjaja, String vaznostDogadjaja) {
        this.id = id;
        this.nazivDogadjaja = nazivDogadjaja;
        this.datumDogadjaja = datumDogadjaja;
        this.vaznostDogadjaja = vaznostDogadjaja;
    }

    public String getNazivDogadjaja() {
        return nazivDogadjaja;
    }

    public void setNazivDogadjaja(String nazivDogadjaja) {
        this.nazivDogadjaja = nazivDogadjaja;
    }

    public Date getDatumDogadjaja() {
        return datumDogadjaja;
    }

    public void setDatumDogadjaja(Date datumDogadjaja) {
        this.datumDogadjaja = datumDogadjaja;
    }

    public String getVaznostDogadjaja() {
        return vaznostDogadjaja;
    }

    public void setVaznostDogadjaja(String vaznostDogadjaja) {
        this.vaznostDogadjaja = vaznostDogadjaja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nazivDogadjaja + "," + datumDogadjaja + "," + vaznostDogadjaja;
    }

    public String stringZaList(){
        return nazivDogadjaja;
    }
}
