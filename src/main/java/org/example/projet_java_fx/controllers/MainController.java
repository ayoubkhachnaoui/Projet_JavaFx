package org.example.projet_java_fx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.projet_java_fx.utils.AnimationUtils;
import org.example.projet_java_fx.utils.NotificationUtils;
import org.example.projet_java_fx.utils.ThemeManager;

import java.io.IOException;

public class MainController {

    @FXML private StackPane contentArea;
    @FXML private Label lblUsername;
    @FXML private Label lblRole;
    @FXML private Button btnStudents, btnModules, btnGrades, btnStats, btnLogout, btnDashboard, btnTheme;

    @FXML
    public void initialize() {
        // Apply hover animations to sidebar buttons
        AnimationUtils.applyButtonHoverAnimation(btnDashboard);
        AnimationUtils.applyButtonHoverAnimation(btnStudents);
        AnimationUtils.applyButtonHoverAnimation(btnModules);
        AnimationUtils.applyButtonHoverAnimation(btnGrades);
        AnimationUtils.applyButtonHoverAnimation(btnStats);
        AnimationUtils.applyButtonHoverAnimation(btnTheme);
        AnimationUtils.applyButtonHoverAnimation(btnLogout);
        
        // Initial view
        showDashboard();
    }

    public void setUserInfo(String username, String role) {
        lblUsername.setText(username);
        lblRole.setText(role);
        
        // Restrict access based on role
        if ("ENSEIGNANT".equals(role)) {
            if (btnStudents != null) btnStudents.setDisable(true);
            if (btnModules != null) btnModules.setDisable(true);
        }
    }

    @FXML
    private void showDashboard() {
        loadView("/org/example/projet_java_fx/stats-view.fxml");
    }

    @FXML
    private void showStudents() {
        loadView("/org/example/projet_java_fx/student-view.fxml");
    }

    @FXML
    private void showModules() {
        loadView("/org/example/projet_java_fx/module-view.fxml");
    }

    @FXML
    private void showGrades() {
        loadView("/org/example/projet_java_fx/grade-view.fxml");
    }

    @FXML
    private void showStats() {
        loadView("/org/example/projet_java_fx/stats-view.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
            // Apply entrance animation
            AnimationUtils.animateViewEntrance(view);
        } catch (IOException e) {
            e.printStackTrace();
            NotificationUtils.showError("Error", "Could not load view: " + fxmlPath);
        }
    }

    @FXML
    private void toggleTheme() {
        ThemeManager.toggleTheme(contentArea.getScene());
        String themeName = ThemeManager.isDarkMode() ? "Dark" : "Light";
        NotificationUtils.showSuccess("Theme Changed", "Active Theme: " + themeName);
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/projet_java_fx/login-view.fxml"));
            Stage stage = (Stage) contentArea.getScene().getWindow();
            Scene scene = new Scene(root);
            ThemeManager.applyTheme(scene);
            stage.setScene(scene);
            stage.setMaximized(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
