package org.example.projet_java_fx.utils;

import javafx.scene.Scene;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final String THEME_KEY = "app_theme";
    private static final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
    private static boolean isDarkMode = getSavedTheme() == Theme.DARK;

    public enum Theme {
        LIGHT, DARK
    }

    public static void applyTheme(Scene scene) {
        Theme theme = getSavedTheme();
        isDarkMode = (theme == Theme.DARK);
        
        scene.getStylesheets().clear();
        
        // Always keep base.css loaded
        String baseCss = ThemeManager.class.getResource("/org/example/projet_java_fx/css/base.css").toExternalForm();
        scene.getStylesheets().add(baseCss);
        
        if (isDarkMode) {
            String darkCss = ThemeManager.class.getResource("/org/example/projet_java_fx/css/dark-theme.css").toExternalForm();
            scene.getStylesheets().add(darkCss);
        } else {
            String lightCss = ThemeManager.class.getResource("/org/example/projet_java_fx/css/light-theme.css").toExternalForm();
            scene.getStylesheets().add(lightCss);
        }
    }

    public static void setTheme(Theme theme) {
        prefs.put(THEME_KEY, theme.name());
        isDarkMode = (theme == Theme.DARK);
    }

    public static Theme getSavedTheme() {
        return Theme.valueOf(prefs.get(THEME_KEY, Theme.LIGHT.name()));
    }

    public static boolean isDarkMode() {
        return isDarkMode;
    }

    public static void toggleTheme(Scene scene) {
        Theme next = isDarkMode ? Theme.LIGHT : Theme.DARK;
        setTheme(next);
        applyTheme(scene);
    }
}

