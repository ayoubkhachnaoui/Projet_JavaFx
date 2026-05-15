package org.example.projet_java_fx.utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimationUtils {

    /**
     * ScaleTransition (1.0 to 1.05) for button hover effects.
     */
    public static void applyButtonHoverAnimation(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        
        button.setOnMouseEntered(e -> {
            st.setFromX(1.0);
            st.setFromY(1.0);
            st.setToX(1.05);
            st.setToY(1.05);
            st.playFromStart();
        });

        button.setOnMouseExited(e -> {
            st.setFromX(1.05);
            st.setFromY(1.05);
            st.setToX(1.0);
            st.setToY(1.0);
            st.playFromStart();
        });
    }

    /**
     * Combined FadeTransition (0 to 1 opacity) and TranslateTransition (Y-axis slide up)
     * for loading new views into the main content area.
     */
    public static void animateViewEntrance(Node node) {
        node.setOpacity(0);
        node.setTranslateY(20);

        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0);
        ft.setToValue(1);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
        tt.setFromY(20);
        tt.setToY(0);

        ft.play();
        tt.play();
    }
}
