/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

/**
 *
 * @author mado
 */
public class Historique {
    int id;
    String id_guichetier;
    String operation; 
    String Date;
    String Heure;

    public Historique(int id,  String operation, String Date, String Heure,String id_guichetier) {
        this.id = id;
        this.operation = operation;
        this.Date = Date;
        this.Heure = Heure;
        this.id_guichetier = id_guichetier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_guichetier() {
        return id_guichetier;
    }

    public void setId_guichetier(String id_guichetier) {
        this.id_guichetier = id_guichetier;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getHeure() {
        return Heure;
    }

    public void setHeure(String Heure) {
        this.Heure = Heure;
    }
    
}
