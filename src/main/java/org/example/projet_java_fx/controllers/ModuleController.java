package org.example.projet_java_fx.controllers; 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projet_java_fx.models.Module;
import org.example.projet_java_fx.services.CSVService;
import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationUtils;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.*;

public class ModuleController {

    @FXML private TextField txtCode, txtNom, txtCoeff, txtEnseignant;
    @FXML private TableView<Module> moduleTable;
    @FXML private TableColumn<Module, String> colCode, colNom, colEnseignant;
    @FXML private TableColumn<Module, Double> colCoeff;

    private ObservableList<Module> moduleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCoeff.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        colEnseignant.setCellValueFactory(new PropertyValueFactory<>("enseignant"));

        loadModules();

        moduleTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtCode.setText(newSelection.getCode());
                txtNom.setText(newSelection.getNom());
                txtCoeff.setText(String.valueOf(newSelection.getCoefficient()));
                txtEnseignant.setText(newSelection.getEnseignant());
            }
        });
    }

    private void loadModules() {
        moduleList.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM modules")) {

            while (rs.next()) {
                moduleList.add(new Module(
                        rs.getString("code"),
                        rs.getString("nom"),
                        rs.getDouble("coefficient"),
                        rs.getString("enseignant")
                ));
            }
            moduleTable.setItems(moduleList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        if (!validateInput()) return;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO modules (code, nom, coefficient, enseignant) VALUES (?,?,?,?)")) {
            
            pstmt.setString(1, txtCode.getText());
            pstmt.setString(2, txtNom.getText());
            pstmt.setDouble(3, Double.parseDouble(txtCoeff.getText()));
            pstmt.setString(4, txtEnseignant.getText());

            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Module ajouté.");
            loadModules();
            handleClear();
        } catch (SQLException e) {
            NotificationUtils.showError("Erreur", "Le code module doit être unique.");
        }
    }

    @FXML
    private void handleUpdate() {
        if (moduleTable.getSelectionModel().getSelectedItem() == null) return;
        if (!validateInput()) return;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE modules SET nom=?, coefficient=?, enseignant=? WHERE code=?")) {
            
            pstmt.setString(1, txtNom.getText());
            pstmt.setDouble(2, Double.parseDouble(txtCoeff.getText()));
            pstmt.setString(3, txtEnseignant.getText());
            pstmt.setString(4, txtCode.getText());

            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Module mis à jour.");
            loadModules();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Module selected = moduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM modules WHERE code=?")) {
            pstmt.setString(1, selected.getCode());
            pstmt.executeUpdate();
            NotificationUtils.showSuccess("Succès", "Module supprimé.");
            loadModules();
            handleClear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClear() {
        txtCode.clear();
        txtNom.clear();
        txtCoeff.clear();
        txtEnseignant.clear();
        moduleTable.getSelectionModel().clearSelection();
    }

    private boolean validateInput() {
        try {
            if (txtCode.getText().isEmpty() || txtNom.getText().isEmpty()) return false;
            double coeff = Double.parseDouble(txtCoeff.getText());
            if (coeff <= 0) {
                NotificationUtils.showWarning("Validation", "Le coefficient doit être supérieur à 0.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            NotificationUtils.showWarning("Validation", "Coefficient invalide.");
            return false;
        }
    }

    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les Modules");
        fileChooser.setInitialFileName("modules.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(moduleTable.getScene().getWindow());
        if (file != null) {
            CSVService.exportTableToCSV("modules", file);
        }
    }

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer des Modules");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(moduleTable.getScene().getWindow());
        if (file != null) {
            CSVService.importCSVToTable("modules", file);
            loadModules();
        }
    }
}
