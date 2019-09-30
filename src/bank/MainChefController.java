
package bank;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainChefController implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Pane paneAjouter;
    @FXML
    private Button BtnAjouter;
    @FXML
    private Button BtnHisto;
    @FXML
    private Button BtnLisGui;
    @FXML
    private Button BtnLisCl;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date;
    @FXML
    private PasswordField repass;
    @FXML
    private Pane paneListGui;
    @FXML
    private TableColumn<Guichetier, String> IDtableGui;
    @FXML
    private TableColumn<Guichetier, String> NomTableGui;
    @FXML
    private TableColumn<Guichetier, String> PrenomTableGui;
    @FXML
    private TableColumn<Guichetier, String> DateTableGui;
    @FXML
    private TableView<Guichetier> TableGui;

    private ObservableList<Guichetier> listgui;
    private ObservableList<Client> listCl;
    private ObservableList<Historique> listHisto;

    @FXML
    private Pane paneListCl;
    @FXML
    private TableView<Client> TableCl;
    @FXML
    private TableColumn<Client, String> IDtableCl;
    @FXML
    private TableColumn<Client, String> NomTableCl;
    @FXML
    private TableColumn<Client, String> DateTableCl;
    @FXML
    private TableColumn<Client, Double> SoldeTableCl;
    @FXML
    private TableColumn<Client, String> PrenomTableCl;
    @FXML
    private TableView<Historique> TableHisto;
    @FXML
    private TableColumn<Historique, Integer> IDOpTableHisto;
    @FXML
    private TableColumn<Historique, String> LabelTableHisto;
    @FXML
    private TableColumn<Historique, String> DateTableHisto;
    @FXML
    private TableColumn<Historique, String> HeureTableHisto;
    @FXML
    private TableColumn<Historique, String> IDGuiTableHisto;
    @FXML
    private Pane paneHisto;
    @FXML
    private Button BtnStatistique;
    @FXML
    private Pane paneStat;
    @FXML
    private TextField nombCl;
    @FXML
    private TextField nombGui;
    @FXML
    private TextField SoldeT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        IDtableGui.setCellValueFactory(new PropertyValueFactory<Guichetier, String>("id"));
        NomTableGui.setCellValueFactory(new PropertyValueFactory<Guichetier, String>("nom"));
        PrenomTableGui.setCellValueFactory(new PropertyValueFactory<Guichetier, String>("prenom"));
        DateTableGui.setCellValueFactory(new PropertyValueFactory<Guichetier, String>("date"));

        IDtableCl.setCellValueFactory(new PropertyValueFactory<Client, String>("id"));
        NomTableCl.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        PrenomTableCl.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        DateTableCl.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        SoldeTableCl.setCellValueFactory(new PropertyValueFactory<Client, Double>("solde"));

        IDOpTableHisto.setCellValueFactory(new PropertyValueFactory<Historique, Integer>("id"));
        LabelTableHisto.setCellValueFactory(new PropertyValueFactory<Historique, String>("operation"));
        DateTableHisto.setCellValueFactory(new PropertyValueFactory<Historique, String>("Date"));
        HeureTableHisto.setCellValueFactory(new PropertyValueFactory<Historique, String>("Heure"));
        IDGuiTableHisto.setCellValueFactory(new PropertyValueFactory<Historique, String>("id_guichetier"));
        try {
            listgui = dataBase.getGui();
            listCl = dataBase.getCl();
            listHisto = dataBase.getHisto();
        } catch (ClassNotFoundException | SQLException e) {
        }

        TableGui.setItems(listgui);
        TableCl.setItems(listCl);
        TableHisto.setItems(listHisto);
        try {
            nombCl.setText(String.valueOf(dataBase.count("client")));
            nombGui.setText(String.valueOf(dataBase.count("guichetier")));
            SoldeT.setText(String.valueOf(dataBase.sum()));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainChefController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean checkID(String id) throws ClassNotFoundException, SQLException {
        Connection con = dataBase.getconnection();
        boolean check = false;

        String sql = "Select * from guichetier where ID = ? ";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            check = true;
        }

        return check;
    }

    private boolean checkUser(String user) throws ClassNotFoundException, SQLException {
        Connection con = dataBase.getconnection();
        boolean check = false;

        String sql = "Select * from guichetier where user = ? ";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, user);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            check = true;
        }
        return check;
    }

    @FXML
    private void confirmer(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        Alert alert;
        String g_id = id.getText();
        String g_nom = nom.getText();
        String g_prenom = prenom.getText();
        LocalDate g_date = date.getValue();
        String g_user = user.getText();
        String g_pass = pass.getText();
        String g_repass = repass.getText();
        if (g_id.equals("")) {
            alert = new Alert(AlertType.INFORMATION, "le champ id est vide");
            alert.showAndWait();
        } else if (g_nom.equals("")) {
            alert = new Alert(AlertType.INFORMATION, "le champ nom est vide");
            alert.showAndWait();
        } else if (g_prenom.equals("")) {
            alert = new Alert(AlertType.INFORMATION, "le champ prenom est vide");
            alert.showAndWait();
        } else if (g_date == null) {
            alert = new Alert(AlertType.INFORMATION, "le champ date est vide");
            alert.showAndWait();
        } else if (g_user.equals("")) {
            alert = new Alert(AlertType.INFORMATION, "le champ nom de l'utilisateur est vide");
            alert.showAndWait();
        } else if (g_pass.equals("")) {
            alert = new Alert(AlertType.INFORMATION, "le champ mot de pass est vide");
            alert.showAndWait();
        } else if (!g_pass.equals(g_repass)) {
            alert = new Alert(AlertType.INFORMATION, "reentrer le mot de pass");
            alert.showAndWait();
        } else if (checkID(g_id)) {
            alert = new Alert(AlertType.INFORMATION, "le ID deja utiliser");
            alert.showAndWait();
        } else if (checkUser(g_user)) {
            alert = new Alert(AlertType.INFORMATION, "le nom de l'utilisateur deja utiliser");
            alert.showAndWait();
        } else {
            String message = " tu est sur d'ajoute ce nuveau guichetier : "
                    + "\nid: " + g_id + "\nnom:" + g_nom + "\nprenom:"
                    + g_prenom + "\ndate de naissance:" + g_date;
            alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
            alert.setHeight(200);
            alert.setWidth(450);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    Connection connection = dataBase.getconnection();
                    PreparedStatement ps;
                    String sql = "Insert into guichetier (ID,Nom,Prenom,DateNaissance,user,pass) values(?,?,?,?,?,?)";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, g_id);
                    ps.setString(2, g_nom);
                    ps.setString(3, g_prenom);
                    ps.setString(4, g_date.toString());
                    ps.setString(5, g_user);
                    ps.setString(6, g_pass);
                    if (ps.executeUpdate() > 0) {
                        alert = new Alert(AlertType.INFORMATION, "guichetier ajouter");
                        alert.showAndWait();
                    }
                } catch (ClassNotFoundException ex) {
                }
            }

            vider(event);
        }

    }

    @FXML
    private void vider(ActionEvent event
    ) {
        id.setText("");
        nom.setText("");
        prenom.setText("");
        date.setValue(null);
        user.setText("");
        pass.setText("");
        repass.setText("");
    }

    @FXML
    private void exit(ActionEvent event
    ) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir quitter", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    @FXML
    private void AjouterVisible(ActionEvent event
    ) {
        paneAjouter.setVisible(true);
        paneListGui.setVisible(false);
        paneListCl.setVisible(false);
        paneHisto.setVisible(false);
        paneStat.setVisible(false);
    }

    URL url;
    ResourceBundle rb;

    @FXML
    private void ListGuiVisible(ActionEvent event
    ) {
        paneListGui.setVisible(true);
        paneAjouter.setVisible(false);
        paneListCl.setVisible(false);
        paneHisto.setVisible(false);
        paneStat.setVisible(false);
        initialize(url, rb);

    }

    @FXML
    private void ListClVisible(ActionEvent event
    ) {
        paneListCl.setVisible(true);
        paneListGui.setVisible(false);
        paneAjouter.setVisible(false);
        paneHisto.setVisible(false);
        paneStat.setVisible(false);
        initialize(url, rb);

    }

    @FXML
    private void HistoVisible(ActionEvent event
    ) {
        paneHisto.setVisible(true);
        paneListCl.setVisible(false);
        paneListGui.setVisible(false);
        paneAjouter.setVisible(false);
        paneStat.setVisible(false);
    }

    @FXML
    private void StatVisible(ActionEvent event
    ) {
        paneStat.setVisible(true);
        paneHisto.setVisible(false);
        paneListCl.setVisible(false);
        paneListGui.setVisible(false);
        paneAjouter.setVisible(false);
        initialize(url, rb);
    }

    @FXML
    private void Déconnecter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
    }

}
