package bank;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView chef;
    @FXML
    private ImageView guichetier;
    @FXML
    private Pane chefPane;
    @FXML
    private Pane guiPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @FXML
    private TextField usergui;
    @FXML
    private PasswordField passgui;
    private static String id_guichetier;

    public static String getId_guichetier() {
        return id_guichetier;
    }

    @FXML
    private TextField userchef;
    @FXML
    private PasswordField passchef;

    private void quitter(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void loginGuich(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        try {
            Connection connection = dataBase.getconnection();
            PreparedStatement ps;
            ResultSet rs;
            String user = usergui.getText();
            String pass = passgui.getText();
            ps = dataBase.getconnection().prepareStatement("select *"
                    + "from guichetier where user=? and pass=?");
            PreparedStatement ps2;
            ResultSet rs2;
            ps2 = dataBase.getconnection().prepareStatement("Select ID "
                    + "from guichetier where user=? and pass=? ");

            ps2.setString(1, user);
            ps2.setString(2, pass);

            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            rs2 = ps2.executeQuery();
            if (rs.next() && rs2.next()) {
                id_guichetier = rs2.getString("ID");
                Parent root = FXMLLoader.load(getClass().getResource(""
                        + "GuichetierMain.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "Votre nom d'utilisateur ou mot de passe est incorrect. Veuillez le vérifier.");
                alert.showAndWait();
                usergui.setText("");
                passgui.setText("");
            }
        } catch (ClassNotFoundException ex) {
        }
    }

    @FXML
    private void loginChef(ActionEvent event) throws IOException, SQLException {

        try {
            Connection connection = dataBase.getconnection();
            PreparedStatement ps;
            ResultSet rs;
            String user = userchef.getText();
            String pass = passchef.getText();
            ps = dataBase.getconnection().prepareStatement("select *"
                    + "from chef where user=? and pass=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                Parent root = FXMLLoader.load(getClass().getResource("MainChef.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(scene);

            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "Votre nom d'utilisateur ou mot de passe est incorrect. "
                        + "Veuillez le vérifier.");
                alert.showAndWait();
                userchef.setText("");
                passchef.setText("");
            }
        } catch (ClassNotFoundException ex) {
        }
    }

    @FXML
    private void gui(MouseEvent event) {
        chefPane.setVisible(false);
        guiPane.setVisible(true);
    }

    @FXML
    private void chef(MouseEvent event) {
        chefPane.setVisible(true);
        guiPane.setVisible(false);
    }
}
