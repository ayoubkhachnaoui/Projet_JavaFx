package org.example.projet_java_fx.services;

import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CSVService {

    public static void exportTableToCSV(String tableName, File file) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
             PrintWriter writer = new PrintWriter(new FileWriter(file))) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write header
            for (int i = 1; i <= columnCount; i++) {
                writer.print(metaData.getColumnName(i));
                if (i < columnCount) writer.print(",");
            }
            writer.println();

            // Write data
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    if (value == null) value = "";
                    // Basic CSV escaping: if contains comma, wrap in quotes
                    if (value.contains(",")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    writer.print(value);
                    if (i < columnCount) writer.print(",");
                }
                writer.println();
            }

            NotificationUtils.showSuccess("Export Réussi", "Les données de " + tableName + " ont été exportées.");

        } catch (Exception e) {
            NotificationUtils.showError("Erreur Export", "Impossible d'exporter les données: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void importCSVToTable(String tableName, File file) {
        try (Connection conn = DatabaseConnection.getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String headerLine = reader.readLine();
            if (headerLine == null) return;

            String[] columns = headerLine.split(",");
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder values = new StringBuilder("VALUES (");

            // We skip 'id' for auto-increment tables if it's the first column and we want DB to handle it
            // But for simplicity, we'll try to insert everything except if the user specifically wants to skip ID.
            // Let's assume we insert all columns present in CSV.
            
            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i]);
                values.append("?");
                if (i < columns.length - 1) {
                    sql.append(", ");
                    values.append(", ");
                }
            }
            sql.append(") ").append(values).append(")");

            try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Regex to handle commas in quotes
                    for (int i = 0; i < row.length; i++) {
                        String val = row[i].trim();
                        if (val.startsWith("\"") && val.endsWith("\"")) {
                            val = val.substring(1, val.length() - 1).replace("\"\"", "\"");
                        }
                        pstmt.setString(i + 1, val.isEmpty() ? null : val);
                    }
                    pstmt.addBatch();
                    count++;
                }
                pstmt.executeBatch();
                NotificationUtils.showSuccess("Import Réussi", count + " lignes importées dans " + tableName);
            }

        } catch (Exception e) {
            NotificationUtils.showError("Erreur Import", "Impossible d'importer les données: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
