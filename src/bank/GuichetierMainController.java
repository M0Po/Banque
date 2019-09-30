package bank;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuichetierMainController implements Initializable {

    @FXML
    private Pane NouveauCopmtePane;
    @FXML
    private Pane VersementPane;
    @FXML
    private Pane VirementPane;
    @FXML
    private Pane RetraitPane;
    @FXML
    private Pane AvoirePane;
    @FXML
    private Pane BlockerPane;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date;
    @FXML
    private TextField solde;
    @FXML
    private TextField idAvoire;
    @FXML
    private TextArea ClientArea;
    @FXML
    private TextField idEmetteur;
    @FXML
    private TextField idRecepteur;
    @FXML
    private TextField montant;
    @FXML
    private TextField idClient;
    @FXML
    private TextField montantDipose;
    @FXML
    private TextField idRetrait;
    @FXML
    private TextField montantRetrait;

    Connection connection;

    public GuichetierMainController() throws ClassNotFoundException {
        this.connection = dataBase.getconnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        solde.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                solde.setText(oldValue);
            }
        });
        montant.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                montant.setText(oldValue);
            }
        });
        montantDipose.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                montantDipose.setText(oldValue);
            }
        });
        montantRetrait.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                montantRetrait.setText(oldValue);
            }
        });

    }

    @FXML
    private void NouveauCompte(ActionEvent event) {
        NouveauCopmtePane.setVisible(true);
        VersementPane.setVisible(false);
        VirementPane.setVisible(false);
        RetraitPane.setVisible(false);
        AvoirePane.setVisible(false);
    }

    @FXML
    private void Versement(ActionEvent event) {
        VersementPane.setVisible(true);
        NouveauCopmtePane.setVisible(false);
        VirementPane.setVisible(false);
        RetraitPane.setVisible(false);
        AvoirePane.setVisible(false);
    }

    @FXML
    private void Virement(ActionEvent event) {
        VirementPane.setVisible(true);
        NouveauCopmtePane.setVisible(false);
        VersementPane.setVisible(false);
        RetraitPane.setVisible(false);
        AvoirePane.setVisible(false);
    }

    @FXML
    private void Retrait(ActionEvent event) {
        RetraitPane.setVisible(true);
        NouveauCopmtePane.setVisible(false);
        VersementPane.setVisible(false);
        VirementPane.setVisible(false);
        AvoirePane.setVisible(false);
    }

    @FXML
    private void Avoire(ActionEvent event) {
        idAvoire.setText("");
        ClientArea.setText("");
        AvoirePane.setVisible(true);
        NouveauCopmtePane.setVisible(false);
        VersementPane.setVisible(false);
        VirementPane.setVisible(false);
        RetraitPane.setVisible(false);
    }

    @FXML
    private void vider(ActionEvent event) {
        id.setText("");
        nom.setText("");
        prenom.setText("");
        solde.setText("");
        date.setValue(null);
    }

    private boolean checkID(String id) throws ClassNotFoundException, SQLException {
        boolean check = false;
        String sql = "Select * from client where ID = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            check = true;
        }
        return check;
    }

    @FXML
    private void confirmer(ActionEvent event) throws ClassNotFoundException, SQLException {
        Alert alert;
        String _id = id.getText();
        String _nom = nom.getText();
        String _prenom = prenom.getText();
        LocalDate _date = date.getValue();
        String _solde = solde.getText();
        if (_id.equals("")) {
            alert = new Alert(AlertType.WARNING, "le champ id est vide");
            alert.showAndWait();
        } else if (_nom.equals("")) {
            alert = new Alert(AlertType.WARNING, "le champ nom est vide");
            alert.showAndWait();
        } else if (_prenom.equals("")) {
            alert = new Alert(AlertType.WARNING, "le champ prenom est vide");
            alert.showAndWait();
        } else if (_date == null) {
            alert = new Alert(AlertType.WARNING, "le champ date est vide");
            alert.showAndWait();
        } else if (_solde.equals("")) {
            alert = new Alert(AlertType.WARNING, "le champ solde est vide");
            alert.showAndWait();
        } else if (checkID(_id)) {
            alert = new Alert(AlertType.WARNING, "le ID deja utiliser");
            alert.showAndWait();
        }
        String sql = "Insert into client (ID,Nom,Prenom,DateNaissance,Solde) value(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, _id);
        ps.setString(2, _nom);
        ps.setString(3, _prenom);
        ps.setString(4, _date.toString());
        ps.setString(5, _solde);

        if (ps.executeUpdate() > 0) {

            alert = new Alert(AlertType.INFORMATION, "Client ajouter");
            alert.showAndWait();
            setOperation("Création d'un nouveau compte avec l'ID: " + _id, java.time.LocalDate.now().toString(), java.time.LocalTime.now().toString(), FXMLDocumentController.getId_guichetier());

        }

        vider(event);

    }

    @FXML
    private void FaireAvoire(ActionEvent event) throws ClassNotFoundException, SQLException {
        String __id = idAvoire.getText();
        String sql = "Select Nom, Prenom,DateNaissance, Solde from client Where ID = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, __id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ClientArea.setText("Nom: " + rs.getString("Nom") + "\n\n"
                    + "Prenom: " + rs.getString("Prenom") + "\n\n"
                    + "Date de naissance: " + rs.getString("DateNaissance") + "\n\n"
                    + "Solde: " + rs.getString("Solde") + " DA");

            setOperation("Fair avoire pour le compte " + __id, java.time.LocalDate.now().toString(), java.time.LocalTime.now().toString(), FXMLDocumentController.getId_guichetier());
        } else {
            Alert alert = new Alert(AlertType.WARNING, "ID n'existe pas");
            alert.showAndWait();
        }
    }

    @FXML
    private void Virer(ActionEvent event) throws ClassNotFoundException, SQLException {
        Alert alert;

        String id1 = idEmetteur.getText();
        String id2 = idRecepteur.getText();
        String mon = montant.getText();
        if (id1.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ id émetteur est vide");
            alert.showAndWait();
        } else if (id2.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ id recepteur est vide");
            alert.showAndWait();
        } else if (mon.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ montant est vide");
            alert.showAndWait();
        } else {
            String sql = "Select Solde from client where ID = ? ";
            PreparedStatement ps, ps2;
            ps = connection.prepareStatement(sql);
            ps2 = connection.prepareStatement(sql);
            ps.setString(1, id1);
            ps2.setString(1, id2);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            if (!rs.next()) {

                alert = new Alert(AlertType.WARNING, "ID d'émetteur n'existe pas");
                alert.showAndWait();

            } else if (!rs2.next()) {

                alert = new Alert(AlertType.WARNING, "ID de récepteur n'existe pas");
                alert.showAndWait();

            } else {
                if (rs.getDouble(1) < Double.valueOf(mon)) {
                    alert = new Alert(AlertType.WARNING, "Solde insuffisant!\nVirement impossible");
                    alert.showAndWait();
                } else {
                    double NouveauSolde1 = 0, NouveauSolde2 = 0;
                    NouveauSolde1 = rs.getDouble(1) - Double.valueOf(mon);
                    NouveauSolde2 = rs2.getDouble(1) + Double.valueOf(mon);
                    PreparedStatement ps3, ps4;
                    String sql2;
                    sql2 = "Update client set Solde = ? Where id = ? ";
                    ps3 = connection.prepareStatement(sql2);
                    ps4 = connection.prepareStatement(sql2);

                    ps3.setDouble(1, NouveauSolde1);
                    ps3.setString(2, id1);
                    ps4.setDouble(1, NouveauSolde2);
                    ps4.setString(2, id2);
                    if (ps3.executeUpdate() > 0 && ps4.executeUpdate() > 0) {
                        alert = new Alert(AlertType.INFORMATION, "Virement réussi");
                        alert.showAndWait();
                        setOperation("Virement de " + mon + " DA de compte " + id1 + " vers le compte " + id2, java.time.LocalDate.now().toString(), java.time.LocalTime.now().toString(), FXMLDocumentController.getId_guichetier());

                    }
                }
            }
        }
     idEmetteur.setText("");
     idRecepteur.setText("");
     montant.setText("");
    }

    @FXML
    private void Verser(ActionEvent event) throws SQLException {
        String sql = "select Solde from client where ID =? ";
        String _id = idClient.getText();
        String mon = montantDipose.getText();
        double _solde = 0;
        Alert alert;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, _id);
        ResultSet rs = ps.executeQuery();
        if (_id.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ id est vide");
            alert.showAndWait();
        } else if (mon.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ montant est vide");
            alert.showAndWait();
        } else if (rs.next()) {
            _solde = rs.getDouble(1);
            _solde += Double.valueOf(mon);
            String sql2 = "update client set Solde = ? Where ID = ? ";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setDouble(1, _solde);
            ps2.setString(2, _id);
            if (ps2.executeUpdate() > 0) {
                alert = new Alert(AlertType.INFORMATION, "Versement réussi");
                alert.showAndWait();
                setOperation("Versement de " + mon + " DA vers le compte " + _id, java.time.LocalDate.now().toString(), java.time.LocalTime.now().toString(), FXMLDocumentController.getId_guichetier());
            }
        } else {
            alert = new Alert(AlertType.WARNING, "ID n'existe pas");
            alert.showAndWait();
        }
        idClient.setText("");
        montantDipose.setText("");
    }

    @FXML
    private void Retirer(ActionEvent event) throws SQLException {
        Alert alert;
        String sql = "select Solde from client where ID =? ";
        String _id = idRetrait.getText();
        double _solde = 0;
        String mon = montantRetrait.getText();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, _id);
        ResultSet rs = ps.executeQuery();
        if (_id.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ id est vide");
            alert.showAndWait();
        } else if (mon.equals("")) {
            alert = new Alert(AlertType.WARNING, " le champ montant est vide");
            alert.showAndWait();
        } else if (rs.next()) {
            _solde = rs.getDouble(1);
            if (_solde < Double.valueOf(mon)) {
                alert = new Alert(AlertType.INFORMATION, "Solde insuffisant! Retrait impossible");
                alert.showAndWait();
            } else {
                _solde -= Double.valueOf(mon);
                String sql2 = "update client set Solde = ? Where ID = ? ";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setDouble(1, _solde);
                ps2.setString(2, _id);
                if (ps2.executeUpdate() > 0) {
                    alert = new Alert(AlertType.INFORMATION, "Retrait réussi ");
                    alert.showAndWait();

                    setOperation("Retrait de " + Double.valueOf(mon) + " DA de le compte " + _id, java.time.LocalDate.now().toString(), java.time.LocalTime.now().toString(), FXMLDocumentController.getId_guichetier());
                }
            }
        } else {
            alert = new Alert(AlertType.WARNING, "ID n'existe pas");
            alert.showAndWait();
        }
        idRetrait.setText("");
        montantRetrait.setText("");
    }

    public void setOperation(String operation, String Date, String Heure, String id_guichetier) throws SQLException {
        String sql = "INSERT INTO `historique` (`ID`,`Operation`, `Date_operation`, `Heure_operation`, `ID_guichetier`) VALUES (NULL,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, operation);
        ps.setString(2, Date);
        ps.setString(3, Heure);
        ps.setString(4, id_guichetier);
        ps.executeUpdate();
    }

    @FXML
    private void Déconnecter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
    }
    
    @FXML
    private void exit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir quitter", ButtonType.NO, ButtonType.YES);
        alert.setTitle("Quitter");
        alert.setHeaderText("header");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

}
