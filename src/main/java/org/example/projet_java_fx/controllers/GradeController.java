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
import org.example.projet_java_fx.services.CSVService;
import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationUtils;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GradeController {

    @FXML private ComboBox<StudentWrapper> cbStudent;
    @FXML private ComboBox<Module> cbModule;
    @FXML private TextField txtCC, txtExamen;
    @FXML private Label lblMoyenne, lblMention;
    @FXML private VBox resultBox;
    @FXML private Button btnSave;
    @FXML private TableView<Grade> gradeTable;
    @FXML private TableColumn<Grade, String> colStudent, colModule, colMention;
    @FXML private TableColumn<Grade, Double> colCC, colExamen, colMoyenne;

    private Map<Integer, String> studentNames = new HashMap<>();
    private Map<String, String> moduleNames = new HashMap<>();

    @FXML
    public void initialize() {
        setupColumns();
        setupBindings();
        loadFormData();
        loadGrades();

        gradeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void setupBindings() {
        // Bind disableProperty to validate selection
        btnSave.disableProperty().bind(
                cbStudent.valueProperty().isNull()
                .or(cbModule.valueProperty().isNull())
                .or(txtCC.textProperty().isEmpty())
                .or(txtExamen.textProperty().isEmpty())
        );

        // Dynamic border color for numerical validation
        txtCC.textProperty().addListener((obs, oldVal, newVal) -> validateNoteField(txtCC, newVal));
        txtExamen.textProperty().addListener((obs, oldVal, newVal) -> validateNoteField(txtExamen, newVal));
    }

    private void validateNoteField(TextField field, String value) {
        try {
            double val = Double.parseDouble(value);
            if (val >= 0 && val <= 20) {
                field.setStyle("-fx-border-color: transparent;");
            } else {
                field.setStyle("-fx-border-color: #ef4444; -fx-border-width: 2px;");
            }
        } catch (NumberFormatException e) {
            field.setStyle("-fx-border-color: #ef4444; -fx-border-width: 2px;");
        }
    }

    private void setupColumns() {
        colStudent.setCellValueFactory(data -> new SimpleStringProperty(studentNames.getOrDefault(data.getValue().getStudentId(), "Unknown")));
        colModule.setCellValueFactory(data -> new SimpleStringProperty(moduleNames.getOrDefault(data.getValue().getModuleCode(), "Unknown")));
        colCC.setCellValueFactory(new PropertyValueFactory<>("noteCC"));
        colExamen.setCellValueFactory(new PropertyValueFactory<>("noteExamen"));
        colMoyenne.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getMoyenne()).asObject());
        
        colMention.setCellFactory(column -> new TableCell<Grade, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label label = new Label(item);
                    label.getStyleClass().add("badge");
                    if (item.contains("Ajourné") || item.contains("Insuffisant")) {
                        label.getStyleClass().add("badge-danger");
                    } else if (item.contains("Passable")) {
                        label.getStyleClass().add("badge-warning");
                    } else if (item.contains("Assez Bien")) {
                        label.getStyleClass().add("badge-info");
                    } else {
                        label.getStyleClass().add("badge-success");
                    }
                    setGraphic(label);
                }
            }
        });
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
            
            NotificationUtils.showSuccess("Succès", "Note enregistrée.");
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
                NotificationUtils.showWarning("Validation", "Les notes doivent être entre 0 et 20.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            NotificationUtils.showWarning("Validation", "Notes invalides.");
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

    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les Notes");
        fileChooser.setInitialFileName("notes.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(gradeTable.getScene().getWindow());
        if (file != null) {
            CSVService.exportTableToCSV("grades", file);
        }
    }

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer des Notes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(gradeTable.getScene().getWindow());
        if (file != null) {
            CSVService.importCSVToTable("grades", file);
            loadGrades();
        }
    }
}
