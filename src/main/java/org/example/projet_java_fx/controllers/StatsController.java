package org.example.projet_java_fx.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.example.projet_java_fx.services.StatisticsService;

import java.sql.SQLException;
import java.util.Map;

public class StatsController {

    @FXML private Label lblTotalStudents, lblClassAverage, lblBestStudent, lblTotalModules;
    @FXML private BarChart<String, Number> barChart;
    @FXML private BarChart<String, Number> successChart;
    @FXML private ComboBox<String> cbFilterNiveau;

    @FXML
    public void initialize() {
        cbFilterNiveau.setItems(FXCollections.observableArrayList("Tous les niveaux", "ING 1", "ING 2", "ING 3", "PREPA 1", "PREPA 2"));
        cbFilterNiveau.setValue("Tous les niveaux");
        
        // Modernize charts
        barChart.setLegendVisible(false);
        successChart.setLegendVisible(false);
        barChart.setAnimated(false);
        successChart.setAnimated(false);
        
        refreshStats();
    }

    @FXML
    public void refreshStats() {
        try {
            // Basic Metrics
            lblTotalStudents.setText(String.valueOf(StatisticsService.getTotalStudents()));
            lblClassAverage.setText(String.format("%.2f", StatisticsService.getClassAverage()));
            lblBestStudent.setText(StatisticsService.getBestStudent());
            lblTotalModules.setText(String.valueOf(StatisticsService.getTotalModules()));

            // Students by Filiere Chart
            XYChart.Series<String, Number> filiereSeries = new XYChart.Series<>();
            filiereSeries.setName("Étudiants");
            Map<String, Integer> filiereStats = StatisticsService.getStudentsByFiliere();
            for (Map.Entry<String, Integer> entry : filiereStats.entrySet()) {
                filiereSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barChart.getData().setAll(filiereSeries);

            // Success Rate Chart
            XYChart.Series<String, Number> successSeries = new XYChart.Series<>();
            successSeries.setName("Taux de Réussite");
            Map<String, Double> successStats = StatisticsService.getSuccessRateByModule();
            for (Map.Entry<String, Double> entry : successStats.entrySet()) {
                successSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            successChart.getData().setAll(successSeries);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
