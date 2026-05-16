package org.example.projet_java_fx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:gestion_etudiants.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            initializeDatabase();
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            // Users table
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)");

            // Students table
            statement.execute("CREATE TABLE IF NOT EXISTS students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom TEXT NOT NULL," +
                    "prenom TEXT NOT NULL," +
                    "cin TEXT UNIQUE NOT NULL," +
                    "email TEXT NOT NULL," +
                    "telephone TEXT NOT NULL," +
                    "date_naissance TEXT NOT NULL," +
                    "niveau TEXT NOT NULL," +
                    "filiere TEXT NOT NULL," +
                    "groupe TEXT NOT NULL)");

            // Modules table
            statement.execute("CREATE TABLE IF NOT EXISTS modules (" +
                    "code TEXT PRIMARY KEY," +
                    "nom TEXT NOT NULL," +
                    "coefficient REAL NOT NULL," +
                    "enseignant TEXT NOT NULL," +
                    "id_affectation TEXT," +
                    "document_path TEXT)");

            // Grades table
            statement.execute("CREATE TABLE IF NOT EXISTS grades (" +
                    "student_id INTEGER," +
                    "module_code TEXT," +
                    "note_cc REAL NOT NULL," +
                    "note_examen REAL NOT NULL," +
                    "PRIMARY KEY (student_id, module_code)," +
                    "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (module_code) REFERENCES modules(code) ON DELETE CASCADE)");

            // Seed default admin if not exists
            statement.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('ayoub', 'khachnaouiayoub', 'ADMIN')");
            statement.execute("INSERT OR IGNORE INTO users (username, password, role) VALUES ('teacher', 'teacher123', 'ENSEIGNANT')");

            // Alter tables to add new columns if they exist but don't have them
            try {
                statement.execute("ALTER TABLE modules ADD COLUMN id_affectation TEXT");
            } catch (SQLException e) {
                // Column might already exist
            }
            try {
                statement.execute("ALTER TABLE modules ADD COLUMN document_path TEXT");
            } catch (SQLException e) {
                // Column might already exist
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
