package org.example.projet_java_fx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.projet_java_fx.utils.AnimationUtils;
import org.example.projet_java_fx.utils.DatabaseConnection;
import org.example.projet_java_fx.utils.NotificationUtils;
import org.example.projet_java_fx.utils.ThemeManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button btnLogin, btnTheme;

    @FXML
    public void initialize() {
        AnimationUtils.applyButtonHoverAnimation(btnLogin);
        AnimationUtils.applyButtonHoverAnimation(btnTheme);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                NotificationUtils.showSuccess("Connexion réussie", "Bienvenue " + username);
                navigateToMain(role);
            } else {
                errorLabel.setText("Identifiants incorrects.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Erreur de base de données.");
        }
    }

    private void navigateToMain(String role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projet_java_fx/main-view.fxml"));
            Parent root = loader.load();
            
            // Pass role to MainController if needed
            MainController mainController = loader.getController();
            mainController.setUserInfo(usernameField.getText(), role);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            ThemeManager.applyTheme(scene);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleTheme() {
        ThemeManager.toggleTheme(usernameField.getScene());
        String themeName = ThemeManager.isDarkMode() ? "Dark" : "Light";
        NotificationUtils.showSuccess("Theme Changed", "Active Theme: " + themeName);
    }
}
