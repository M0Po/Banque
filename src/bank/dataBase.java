package bank;

import java.sql.*;
import javafx.collections.*;

public class dataBase {

    public static Connection getconnection() throws ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank", "root", "");
        } catch (SQLException e) {
        }
        return connection;
    }

    public static ObservableList<Guichetier> getGui() throws ClassNotFoundException, SQLException {
        Connection con = getconnection();
        ObservableList<Guichetier> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT ID, Nom, Prenom, DateNaissance from guichetier ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Guichetier(rs.getString("ID"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("DateNaissance")));
            }
        } catch (SQLException e) {
        } finally {
            con.close();
        }
        return list;
    }

    public static ObservableList<Client> getCl() throws ClassNotFoundException, SQLException {
        Connection con = getconnection();
        ObservableList<Client> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT ID, Nom, Prenom, DateNaissance,Solde From client ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Client(rs.getString("ID"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("DateNaissance"), rs.getDouble("Solde")));
            }
        } catch (SQLException e) {
        } finally {
            con.close();
        }
        return list;

    }

    public static ObservableList<Historique> getHisto() throws ClassNotFoundException, SQLException {
        Connection con = getconnection();
        ObservableList<Historique> list = FXCollections.observableArrayList();
        try {
            String sql = "SELECT ID, Operation, Date_operation, Heure_operation,ID_guichetier From historique ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Historique(rs.getInt("ID"), rs.getString("Operation"), rs.getString("Date_operation"),rs.getString("Heure_operation"), rs.getString("ID_guichetier")));
            }
        } catch (SQLException e) {
        } finally {
            con.close();
        }
        return list;
    }

    public static int count(String table) throws ClassNotFoundException, SQLException {
        int nombre = 0;
        Connection connection = getconnection();
        String sql = "select count(ID) from " + table;
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nombre = rs.getInt(1);
        }
        return nombre;
    }

    public static double sum() throws ClassNotFoundException, SQLException {
        double sum = 0;
        Connection connection = getconnection();
        String sql = "select sum(solde) from client";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            sum = rs.getDouble(1);
        }
        return sum;
    }

}
