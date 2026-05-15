package org.example.projet_java_fx.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to seed the database with Tunisian test data.
 */
public class DataSeeder {

    public static void seedData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try (Statement stmt = conn.createStatement()) {
                // Clear existing data for a clean test state
                stmt.execute("DELETE FROM grades");
                stmt.execute("DELETE FROM modules");
                stmt.execute("DELETE FROM students");
                
                // 1. Students
                String studentSql = "INSERT INTO students (nom, prenom, cin, email, telephone, date_naissance, niveau, filiere, groupe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(studentSql)) {
                    addStudent(pstmt, "Ben Ammar", "Mohamed", "09876543", "mohamed.benammar@esprit.tn", "55123456", "2001-03-12", "ING 1", "Informatique", "G1");
                    addStudent(pstmt, "Trabelsi", "Ines", "11223344", "ines.trabelsi@enit.tn", "22334455", "2002-07-25", "ING 1", "Informatique", "G1");
                    addStudent(pstmt, "Mansouri", "Ahmed", "07654321", "ahmed.mansouri@supcom.tn", "98765432", "2001-11-05", "ING 2", "Télécom", "G2");
                    addStudent(pstmt, "Jendoubi", "Myriam", "12341234", "myriam.jendoubi@insat.tn", "44556677", "2002-01-15", "ING 1", "Informatique", "G3");
                    addStudent(pstmt, "Gharbi", "Yassine", "00112233", "yassine.gharbi@fst.tn", "50607080", "2000-09-30", "ING 3", "Informatique", "G1");
                    addStudent(pstmt, "Selmi", "Sonia", "44556677", "sonia.selmi@enit.tn", "21222324", "2001-05-14", "ING 2", "Génie Civil", "G2");
                    addStudent(pstmt, "Kammoun", "Amine", "88776655", "amine.kammoun@isg.tn", "99887766", "2002-12-20", "ING 1", "Informatique", "G1");
                    addStudent(pstmt, "Dridi", "Fatma", "33445566", "fatma.dridi@uib.tn", "52334455", "2001-08-08", "ING 2", "Informatique", "G2");
                    addStudent(pstmt, "Ayari", "Slim", "66778899", "slim.ayari@esprit.tn", "20112233", "2000-02-28", "ING 3", "Informatique", "G1");
                    addStudent(pstmt, "Mejri", "Nour", "99001122", "nour.mejri@isg.tn", "58667788", "2002-04-10", "ING 1", "Gestion", "G4");
                    pstmt.executeBatch();
                }

                // 2. Modules
                String moduleSql = "INSERT INTO modules (code, nom, coefficient, enseignant) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(moduleSql)) {
                    addModule(pstmt, "JAVA101", "Programmation Java FX", 3.0, "Prof. Dridi");
                    addModule(pstmt, "PYTH202", "Data Science avec Python", 2.5, "Mme. Selmi");
                    addModule(pstmt, "MATH301", "Analyse Numérique", 4.0, "Prof. Ben Ammar");
                    addModule(pstmt, "DB404", "Bases de Données Avancées", 3.0, "Mr. Mansouri");
                    addModule(pstmt, "WEB505", "Développement Web Moderne", 2.5, "Mme. Gharbi");
                    addModule(pstmt, "SOFT606", "Soft Skills & Communication", 1.5, "Mme. Ayari");
                    pstmt.executeBatch();
                }

                // 3. Grades (A few examples)
                String gradeSql = "INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(gradeSql)) {
                    addGrade(pstmt, 1, "JAVA101", 14.5, 16.0);
                    addGrade(pstmt, 1, "MATH301", 12.0, 10.5);
                    addGrade(pstmt, 2, "JAVA101", 16.0, 18.5);
                    addGrade(pstmt, 3, "PYTH202", 11.0, 12.5);
                    addGrade(pstmt, 4, "JAVA101", 18.0, 19.0);
                    pstmt.executeBatch();
                }

                conn.commit();
                System.out.println("Database seeded successfully with Tunisian names!");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(PreparedStatement pstmt, String nom, String prenom, String cin, String email, String tel, String bday, String niv, String fil, String grp) throws SQLException {
        pstmt.setString(1, nom);
        pstmt.setString(2, prenom);
        pstmt.setString(3, cin);
        pstmt.setString(4, email);
        pstmt.setString(5, tel);
        pstmt.setString(6, bday);
        pstmt.setString(7, niv);
        pstmt.setString(8, fil);
        pstmt.setString(9, grp);
        pstmt.addBatch();
    }

    private static void addModule(PreparedStatement pstmt, String code, String nom, double coeff, String ens) throws SQLException {
        pstmt.setString(1, code);
        pstmt.setString(2, nom);
        pstmt.setDouble(3, coeff);
        pstmt.setString(4, ens);
        pstmt.addBatch();
    }

    private static void addGrade(PreparedStatement pstmt, int sid, String code, double cc, double ex) throws SQLException {
        pstmt.setInt(1, sid);
        pstmt.setString(2, code);
        pstmt.setDouble(3, cc);
        pstmt.setDouble(4, ex);
        pstmt.addBatch();
    }
}
