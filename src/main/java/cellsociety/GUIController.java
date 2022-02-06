package cellsociety;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GUIController {
    private final double frameDelay;
    private final String local;
    private final Stage stage;
    private ResourceBundle languageResources;
    private Theme theme;
    private static final double WIDTH = 600;
    private static final double HEIGHT = 750;




    public enum Theme {
        DEFAULT("Default"),
        FIRE_SPREAD("Light"),
        DARK("Dark");

        private final String bundleName;

        Theme(String s) {
            this.bundleName = s;
        }

        @Override
        public String toString() {
            return this.bundleName;
        }
}
