package org.example.projet_java_fx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.projet_java_fx.models.Grade;
import org.example.projet_java_fx.models.Module;
import org.example.projet_java_fx.models.Student;
import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationService;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GradeController {

    @FXML private ComboBox<StudentWrapper> cbStudent;
    @FXML private ComboBox<Module> cbModule;
    @FXML private TextField txtCC, txtExamen;
    @FXML private Label lblMoyenne, lblMention;
    @FXML private VBox resultBox;
    @FXML private TableView<Grade> gradeTable;
    @FXML private TableColumn<Grade, String> colStudent, colModule, colMention;
    @FXML private TableColumn<Grade, Double> colCC, colExamen, colMoyenne;

    private Map<Integer, String> studentNames = new HashMap<>();
    private Map<String, String> moduleNames = new HashMap<>();

    @FXML
    public void initialize() {
        setupColumns();
        loadFormData();
        loadGrades();

        gradeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void setupColumns() {
        colStudent.setCellValueFactory(data -> new SimpleStringProperty(studentNames.getOrDefault(data.getValue().getStudentId(), "Unknown")));
        colModule.setCellValueFactory(data -> new SimpleStringProperty(moduleNames.getOrDefault(data.getValue().getModuleCode(), "Unknown")));
        colCC.setCellValueFactory(new PropertyValueFactory<>("noteCC"));
        colExamen.setCellValueFactory(new PropertyValueFactory<>("noteExamen"));
        colMoyenne.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getMoyenne()).asObject());
        colMention.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMention()));
    }

    private void loadFormData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Load Students
            ResultSet rsS = conn.createStatement().executeQuery("SELECT id, nom, prenom FROM students");
            ObservableList<StudentWrapper> students = FXCollections.observableArrayList();
            while (rsS.next()) {
                String fullName = rsS.getString("nom") + " " + rsS.getString("prenom");
                studentNames.put(rsS.getInt("id"), fullName);
                students.add(new StudentWrapper(rsS.getInt("id"), fullName));
            }
            cbStudent.setItems(students);

            // Load Modules
            ResultSet rsM = conn.createStatement().executeQuery("SELECT code, nom FROM modules");
            ObservableList<Module> modules = FXCollections.observableArrayList();
            while (rsM.next()) {
                moduleNames.put(rsM.getString("code"), rsM.getString("nom"));
                modules.add(new Module(rsM.getString("code"), rsM.getString("nom"), 0, ""));
            }
            cbModule.setItems(modules);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadGrades() {
        ObservableList<Grade> grades = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM grades")) {
            while (rs.next()) {
                grades.add(new Grade(
                        rs.getInt("student_id"),
                        rs.getString("module_code"),
                        rs.getDouble("note_cc"),
                        rs.getDouble("note_examen")
                ));
            }
            gradeTable.setItems(grades);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave() {
        if (!validateInput()) return;

        StudentWrapper student = cbStudent.getValue();
        Module module = cbModule.getValue();
        double cc = Double.parseDouble(txtCC.getText());
        double ex = Double.parseDouble(txtExamen.getText());

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO grades (student_id, module_code, note_cc, note_examen) VALUES (?,?,?,?) " +
                             "ON CONFLICT(student_id, module_code) DO UPDATE SET note_cc=excluded.note_cc, note_examen=excluded.note_examen")) {
            
            pstmt.setInt(1, student.id);
            pstmt.setString(2, module.getCode());
            pstmt.setDouble(3, cc);
            pstmt.setDouble(4, ex);

            pstmt.executeUpdate();
            
            Grade g = new Grade(student.id, module.getCode(), cc, ex);
            lblMoyenne.setText(String.format("%.2f", g.getMoyenne()));
            lblMention.setText(g.getMention());
            resultBox.setVisible(true);
            
            NotificationService.showSuccess("Succès", "Note enregistrée.");
            loadGrades();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateForm(Grade g) {
        for (StudentWrapper sw : cbStudent.getItems()) {
            if (sw.id == g.getStudentId()) {
                cbStudent.setValue(sw);
                break;
            }
        }
        for (Module m : cbModule.getItems()) {
            if (m.getCode().equals(g.getModuleCode())) {
                cbModule.setValue(m);
                break;
            }
        }
        txtCC.setText(String.valueOf(g.getNoteCC()));
        txtExamen.setText(String.valueOf(g.getNoteExamen()));
        lblMoyenne.setText(String.format("%.2f", g.getMoyenne()));
        lblMention.setText(g.getMention());
        resultBox.setVisible(true);
    }

    private boolean validateInput() {
        try {
            if (cbStudent.getValue() == null || cbModule.getValue() == null) return false;
            double cc = Double.parseDouble(txtCC.getText());
            double ex = Double.parseDouble(txtExamen.getText());
            if (cc < 0 || cc > 20 || ex < 0 || ex > 20) {
                NotificationService.showWarning("Validation", "Les notes doivent être entre 0 et 20.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            NotificationService.showWarning("Validation", "Notes invalides.");
            return false;
        }
    }

    // Helper class for ComboBox
    private static class StudentWrapper {
        int id;
        String name;
        StudentWrapper(int id, String name) { this.id = id; this.name = name; }
        @Override public String toString() { return name; }
    }
}
