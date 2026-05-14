package org.example.projet_java_fx.utils;

import javafx.scene.Scene;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final String THEME_KEY = "app_theme";
    private static final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);

    public enum Theme {
        LIGHT, DARK
    }

    public static void applyTheme(Scene scene) {
        Theme theme = getSavedTheme();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(ThemeManager.class.getResource("/org/example/projet_java_fx/css/base.css").toExternalForm());
        
        if (theme == Theme.DARK) {
            scene.getStylesheets().add(ThemeManager.class.getResource("/org/example/projet_java_fx/css/dark-theme.css").toExternalForm());
        } else {
            scene.getStylesheets().add(ThemeManager.class.getResource("/org/example/projet_java_fx/css/light-theme.css").toExternalForm());
        }
    }

    public static void setTheme(Theme theme) {
        prefs.put(THEME_KEY, theme.name());
    }

    public static Theme getSavedTheme() {
        return Theme.valueOf(prefs.get(THEME_KEY, Theme.LIGHT.name()));
    }

    public static void toggleTheme(Scene scene) {
        Theme current = getSavedTheme();
        Theme next = (current == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
        setTheme(next);
        applyTheme(scene);
    }
}
