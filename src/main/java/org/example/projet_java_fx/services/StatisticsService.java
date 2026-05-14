package org.example.projet_java_fx.services;

import org.example.projet_java_fx.utils.DatabaseConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StatisticsService {

    public static int getTotalStudents() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM students")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public static double getClassAverage() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(
                     "SELECT AVG((note_cc * 0.4) + (note_examen * 0.6)) FROM grades")) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }

    public static Map<String, Integer> getStudentsByFiliere() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(
                     "SELECT filiere, COUNT(*) FROM students GROUP BY filiere")) {
            while (rs.next()) {
                stats.put(rs.getString(1), rs.getInt(2));
            }
        }
        return stats;
    }

    public static String getBestStudent() throws SQLException {
        String sql = "SELECT s.nom, s.prenom, AVG((g.note_cc * 0.4) + (g.note_examen * 0.6)) as moy " +
                     "FROM students s JOIN grades g ON s.id = g.student_id " +
                     "GROUP BY s.id ORDER BY moy DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            return rs.next() ? rs.getString(1) + " " + rs.getString(2) : "N/A";
        }
    }

    public static int getTotalModules() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM modules")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public static Map<String, Double> getSuccessRateByModule() throws SQLException {
        Map<String, Double> stats = new HashMap<>();
        String sql = "SELECT m.nom, " +
                     "SUM(CASE WHEN (g.note_cc * 0.4 + g.note_examen * 0.6) >= 10 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) " +
                     "FROM modules m JOIN grades g ON m.code = g.module_code " +
                     "GROUP BY m.code";
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                stats.put(rs.getString(1), rs.getDouble(2));
            }
        }
        return stats;
    }
}
