//package cellsociety;
//
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.layout.HBox;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import cellsociety.GUIController.Language;
//import cellsociety.GUIController.Theme;
//import java.util.ResourceBundle;
//import javafx.geometry.Pos;
//import javafx.scene.layout.Pane;
//
//
//public class Selector extends Scene{
//    private ResourceBundle resources;
//    private Button fileLoadButton;
//    private final Group root;
//    private final GUIController guicontroller;
//    private final double width;
//    private final double height;
//
//
//    public Selector(GUIController guicontroller, double width, double height, ResourceBundle resources) {
//        super(new Group(), width, height);
//        this.root = (Group) this.getRoot();
//        this.width = width;
//        this.height = height;
//        this.resources = resources;
//        this.guicontroller = guicontroller;
//        setScene();
//    }
//
//
//    private void changeLanguage(Language lang) {
//        this.resources = ResourceBundle.getBundle(guicontroller.RESOURCE_PATH + lang);
//        guicontroller.setLanguage(lang);
//        fileLoadButton.setText(resources.getString("LoadSimulationXML"));
//        guicontroller.setTitle(resources.getString("Launch"));
//    }
//
//
//    private void changeTheme(Theme theme) {
//        guicontroller.setTheme(theme);
//        this.getStylesheets().clear();
//        this.getStylesheets().add(getClass().getResource("styles/" + theme + ".css").toExternalForm());
//    }
//
//
//    private void setScene() {
//        guicontroller.setTitle(resources.getString("Launch"));
//        BorderPane borderPane = new BorderPane();
//        this.fileLoadButton = new Button();
//        borderPane.setCenter(fileLoadButton);
//        borderPane.setTop(createSettings());
//        fileLoadButton.setText(resources.getString("LoadSimulationXML"));
//        fileLoadButton.setOnAction(event -> guicontroller.loadNewSimulation());
//        borderPane.setPrefWidth(width);
//        borderPane.setPrefHeight(height);
//        addNode(borderPane);
//    }
//
//    private Pane createSettings() {
//        HBox row = new HBox(20);
//        row.setAlignment(Pos.CENTER);
//        Label langIcon = createIcon("IMAGELOCATION");
//        Label themeIcon = createIcon("IMAGELOCATION");
//        ComboBox<Language> language = new ComboBox<>();
//        language.getItems().addAll(Language.values());
//        language.setValue(Language.values()[0]);
//        language.setOnAction(e -> changeLanguage(language.getValue()));
//
//        ComboBox<Theme> theme = new ComboBox<>();
//        theme.getItems().addAll(Theme.values());
//        theme.setValue(Theme.values()[0]);
//        theme.setOnAction(e -> changeTheme(theme.getValue()));
//
//        row.getChildren().addAll(langIcon, language, themeIcon, theme);
//
//        return row;
//    }
//
//
//    private Label createIcon(String name) {
//        Label label = new Label();
//        Image labelImg = new Image(getClass().getResourceAsStream(name));
//        ImageView labelIcon = new ImageView(labelImg);
//        label.setGraphic(labelIcon);
//        return label;
//    }
//
//    private void addNode(Node n) {
//        root.getChildren().add(n);
//    }
//
//}
