package bank;

public class Client {

    String id;
    String nom; 
    String prenom;
    String date;
    Double solde;

    public Client(String id, String nom, String prenom, String date, Double solde) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.solde = solde;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }
    
    
}
