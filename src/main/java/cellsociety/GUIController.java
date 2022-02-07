//package cellsociety;
//import javafx.scene.paint.Paint;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import java.io.File;
//import java.util.ResourceBundle;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//
//public class GUIController {
//    private static final double frameDelay;
//    private static final String local;
//    private final Stage stage;
//    public static final String RESOURCE_PATH = "cellsociety.recources.";
//    private ResourceBundle languageResources;
//    private Theme theme;
//    private final CellularAutomata controllerClass;
//    private static final double WIDTH = 600;
//    private static final double HEIGHT = 750;
//
//    public GUIController(Stage primaryStage, double frameDelay, String locale) {
//        this.stage = primaryStage;
//        this.languageResources = ResourceBundle.getBundle(RESOURCE_PATH + Language.ENGLISH);
//        this.controllerClass = new CellularAutomata(this, languageResources);
//        this.stage.setResizable(false);
//        this.frameDelay = frameDelay;
//        this.local = locale;
//        this.theme = Theme.DEFAULT;
//        startUpdates();
//        presentLoadSimScene();
//    }
//
//    private void startUpdates() {
//        KeyFrame frame = new KeyFrame(Duration.seconds(frameDelay), e -> refresh(frameDelay));
//        Timeline animation = new Timeline();
//        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.getKeyFrames().add(frame);
//        animation.play();
//    }
//
//    protected void setTitle(String title) {
//        stage.setTitle(title);
//    }
//    protected File openFile() {
//        FileChooser filec = new FileChooser();
//        Stage stage = new Stage();
//        return filec.showOpenDialog(stage);
//    }
//
//    protected File saveFile() {
//        FileChooser filec = new FileChooser();
//        Stage stage = new Stage();
//        return filec.showSaveDialog(stage);
//    }
//
//    private void refresh(double elapsedTime) {
//        controllerClass.update(elapsedTime);
//    }
//
//
//    private void presentLoadSimScene() {
//        this.stage.setScene(new Selector(this, WIDTH, HEIGHT,
//                languageResources));
//        this.stage.show();
//    }
//
//
//
//
//
//    protected void exitSimulation() {
//        controllerClass.stopSim();
//        stage.setScene(new Selector(this, WIDTH, HEIGHT,
//                languageResources));
//    }
//
//    protected void loadNewSimulation() {
//        controllerClass.setUpSimulation(600, 600, Paint.valueOf("Black"));
//    }
//
//
//    protected void showSimulation(CellularAutomata controllerClass) {
//        Selector selector =
//                new Selector(this.controllerClass, WIDTH, HEIGHT, this.languageResources, this.theme);
//        this.stage.setScene(selector);
//    }
//
//
//
//
//
//    protected void giveException(Exception e) {
//        Alert a = new Alert(AlertType.ERROR, e.getMessage());
//        e.printStackTrace();
//        a.show();
//    }
//
//
//    protected static void createNewControlledStage() {
//        Stage s = new Stage();
//        new GUIController(s, frameDelay, local);
//        s.show();
//    }
//
//
//    protected void setTheme(Theme theme) {
//        this.theme = theme;
//    }
//
//    protected void setLanguage(Language lang) {
//        this.languageResources = ResourceBundle.getBundle(RESOURCE_PATH + lang);
//    }
//
//    public enum Theme {
//        DEFAULT("Default"),
//        FIRE_SPREAD("Light"),
//        DARK("Dark");
//
//        private final String bundle;
//
//        Theme(String s) {
//            this.bundle = s;
//        }
//
//        @Override
//        public String toString() {
//            return this.bundle;
//        }
//    }
//
//    public enum Language {
//        ENGLISH("English"),
//        FRENCH("French"),
//        SPANISH("Spanish");
//
//        private final String bundle;
//
//        Language(String s) {
//            this.bundle = s;
//        }
//
//        @Override
//        public String toString() {
//            return this.bundle;
//        }
//    }
//
//}
