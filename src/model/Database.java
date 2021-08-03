package model;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {

    public static Database instance;

    private List<Dogadjaj> dogadjajiLista;

    static {
        instance = new Database();
    }

    public Database(){
        dogadjajiLista = new ArrayList<>();
        load();
    }

    public static Database getInstance() {
        return instance;
    }

    private void load() {
        try {
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yoxtgC2qlb", "yoxtgC2qlb", "AncWWRgRAb");

            Statement myStatement = myConnection.createStatement();

            ResultSet myRS = myStatement.executeQuery("select * from Dogadjaji");

            while(myRS.next()){
                int id = myRS.getInt("id");
                String naziv = myRS.getString("naziv");
                Date datum = myRS.getDate("datum");
                String vaznost = myRS.getString("vaznost");

                Dogadjaj d = new Dogadjaj(id, naziv, datum, vaznost);
                dogadjajiLista.add(d);
            }
            myConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Dogadjaj> getDogadjajiLista() {
        return dogadjajiLista;
    }


}
