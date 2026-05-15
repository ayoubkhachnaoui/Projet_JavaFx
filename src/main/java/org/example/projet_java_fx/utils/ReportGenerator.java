package org.example.projet_java_fx.utils;

import org.example.projet_java_fx.utils.NotificationUtils;

import java.io.PrintWriter;
import java.sql.*;

public class ReportGenerator {

    public static void exportStudentsToCSV(String filePath) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students");
             PrintWriter writer = new PrintWriter(filePath)) {

            writer.println("ID,Nom,Prenom,CIN,Email,Niveau,Filiere");
            while (rs.next()) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("email"),
                        rs.getString("niveau"),
                        rs.getString("filiere"));
            }
            NotificationUtils.showSuccess("Export Réussi", "La liste des étudiants a été exportée vers " + filePath);

        } catch (Exception e) {
            NotificationUtils.showError("Erreur Export", "Impossible d'exporter les données.");
            e.printStackTrace();
        }
    }
}
