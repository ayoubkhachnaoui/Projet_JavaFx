package org.example.projet_java_fx.controllers; 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projet_java_fx.models.Student;
import org.example.projet_java_fx.services.CSVService;
import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationUtils;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class StudentController {

    @FXML private TextField txtNom, txtPrenom, txtCin, txtEmail, txtTelephone, txtFiliere, txtGroupe, txtSearch;
    @FXML private DatePicker dpDateNaissance;
    @FXML private ComboBox<String> cbNiveau;
    @FXML private Button btnAdd, btnUpdate;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colNom, colPrenom, colCin, colEmail, colNiveau, colFiliere;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupBindings();
        cbNiveau.setItems(FXCollections.observableArrayList("ING 1", "ING 2", "ING 3", "PREPA 1", "PREPA 2"));
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        colFiliere.setCellValueFactory(new PropertyValueFactory<>("filiere"));

        loadStudents();

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void setupBindings() {
        // Bind disableProperty to validate that txtNom and txtPrenom are not empty
        btnAdd.disableProperty().bind(
                txtNom.textProperty().isEmpty()
                .or(txtPrenom.textProperty().isEmpty())
        );
        btnUpdate.disableProperty().bind(
                txtNom.textProperty().isEmpty()
                .or(txtPrenom.textProperty().isEmpty())
        );

        // Dynamically change the -fx-border-color of a TextField to red if the input fails regex validation
        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                txtEmail.setStyle("-fx-border-color: transparent;");
            } else {
                txtEmail.setStyle("-fx-border-color: #ef4444; -fx-border-width: 2px;");
            }
        });
    }

    private void loadStudents() {
        studentList.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                studentList.add(new Student(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("cin"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        LocalDate.parse(rs.getString("date_naissance")),
                        rs.getString("niveau"),
                        rs.getString("filiere"),
                        rs.getString("groupe")
                ));
            }
            studentTable.setItems(studentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        if (!validateInput()) return;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO students (nom, prenom, cin, email, telephone, date_naissance, niveau, filiere, groupe) VALUES (?,?,?,?,?,?,?,?,?)")) {
            
            pstmt.setString(1, txtNom.getText());
            pstmt.setString(2, txtPrenom.getText());
            pstmt.setString(3, txtCin.getText());
            pstmt.setString(4, txtEmail.getText());
            pstmt.setString(5, txtTelephone.getText());
            pstmt.setString(6, dpDateNaissance.getValue().toString());
            pstmt.setString(7, cbNiveau.getValue());
            pstmt.setString(8, txtFiliere.getText());
            pstmt.setString(9, txtGroupe.getText());

            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Étudiant ajouté avec succès.");
            loadStudents();
            handleClear();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                NotificationUtils.showError("Erreur", "Un étudiant avec ce CIN existe déjà.");
            } else {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            NotificationUtils.showWarning("Attention", "Veuillez sélectionner un étudiant.");
            return;
        }

        if (!validateInput()) return;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE students SET nom=?, prenom=?, cin=?, email=?, telephone=?, date_naissance=?, niveau=?, filiere=?, groupe=? WHERE id=?")) {
            
            pstmt.setString(1, txtNom.getText());
            pstmt.setString(2, txtPrenom.getText());
            pstmt.setString(3, txtCin.getText());
            pstmt.setString(4, txtEmail.getText());
            pstmt.setString(5, txtTelephone.getText());
            pstmt.setString(6, dpDateNaissance.getValue().toString());
            pstmt.setString(7, cbNiveau.getValue());
            pstmt.setString(8, txtFiliere.getText());
            pstmt.setString(9, txtGroupe.getText());
            pstmt.setInt(10, selected.getId());

            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Étudiant mis à jour.");
            loadStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            NotificationUtils.showWarning("Attention", "Veuillez sélectionner un étudiant.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM students WHERE id=?")) {
            pstmt.setInt(1, selected.getId());
            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Étudiant supprimé.");
            loadStudents();
            handleClear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        txtNom.clear();
        txtPrenom.clear();
        txtCin.clear();
        txtEmail.clear();
        txtTelephone.clear();
        dpDateNaissance.setValue(null);
        cbNiveau.setValue(null);
        txtFiliere.clear();
        txtGroupe.clear();
        studentTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSearch() {
        String query = txtSearch.getText().toLowerCase();
        if (query.isEmpty()) {
            loadStudents();
            return;
        }

        ObservableList<Student> filtered = FXCollections.observableArrayList();
        for (Student s : studentList) {
            if (s.getNom().toLowerCase().contains(query) || 
                s.getPrenom().toLowerCase().contains(query) || 
                s.getCin().toLowerCase().contains(query) || 
                s.getFiliere().toLowerCase().contains(query)) {
                filtered.add(s);
            }
        }
        studentTable.setItems(filtered);
    }

    private void populateForm(Student s) {
        txtNom.setText(s.getNom());
        txtPrenom.setText(s.getPrenom());
        txtCin.setText(s.getCin());
        txtEmail.setText(s.getEmail());
        txtTelephone.setText(s.getTelephone());
        dpDateNaissance.setValue(s.getDateNaissance());
        cbNiveau.setValue(s.getNiveau());
        txtFiliere.setText(s.getFiliere());
        txtGroupe.setText(s.getGroupe());
    }

    private boolean validateInput() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtCin.getText().isEmpty() ||
            txtEmail.getText().isEmpty() || cbNiveau.getValue() == null) {
            NotificationUtils.showWarning("Validation", "Veuillez remplir les champs obligatoires.");
            return false;
        }
        if (!txtEmail.getText().contains("@")) {
            NotificationUtils.showWarning("Validation", "Email invalide.");
            return false;
        }
        if (!txtTelephone.getText().matches("\\d+")) {
            NotificationUtils.showWarning("Validation", "Le téléphone doit contenir uniquement des chiffres.");
            return false;
        }
        return true;
    }

    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les Étudiants");
        fileChooser.setInitialFileName("etudiants.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(studentTable.getScene().getWindow());
        if (file != null) {
            CSVService.exportTableToCSV("students", file);
        }
    }

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer des Étudiants");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(studentTable.getScene().getWindow());
        if (file != null) {
            CSVService.importCSVToTable("students", file);
            loadStudents();
        }
    }
}
