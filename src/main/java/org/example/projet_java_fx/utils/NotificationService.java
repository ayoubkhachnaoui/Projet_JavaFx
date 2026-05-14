package org.example.projet_java_fx.utils;

import org.controlsfx.control.Notifications;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class NotificationService {

    public static void showSuccess(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle() // Use built-in dark style as base for modern look
                .showInformation();
    }

    public static void showError(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle()
                .showError();
    }

    public static void showWarning(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .showWarning();
    }
}
