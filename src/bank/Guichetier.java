package bank;

public class Guichetier {
    
    String id;
    String nom;
    String prenom;
    String date;

    public Guichetier(String id, String nom, String prenom, String date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate(String date) {
        this.date = date;
    }

   
    
}
