package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import java.util.ResourceBundle;
import cellsociety.GUIController.Theme;
import javafx.scene.Node;
/**
 *
 * @author Haris Adnan
 * @author Matthew Knox
 * @author Edison Ooi
 */

public class Displayer extends Scene {

    private final CellularAutomata controllerClass;
    private final Group root;
    private final double WIDTH;
    private final double HEIGHT;
    private final ResourceBundle resources;

    public Displayer(
        CellularAutomata controllerClass, double width, double height,
        ResourceBundle resources, Theme theme) {
            super(new Group(), width, height);
            this.root = (Group) this.getRoot();
            this.controllerClass = controllerClass;
            this.resources = resources;
            this.WIDTH = width;
            this.HEIGHT = height;
            this.getStylesheets().add(getClass().getResource("styles/" + theme + ".css").toExternalForm());
            buildScene();
        }
        private HBox firstButtonRow() {
            HBox row = new HBox(10);

            Button playButton = new Button(resources.getString("Play"));
            Button speedUpButton = new Button(resources.getString("SpeedUp"));
            Button slowDownButton = new Button(resources.getString("SlowDown"));
            Button pauseButton = new Button(resources.getString("Pause"));
            Button stepButton = new Button(resources.getString("Step"));
            Button extraSettingsButton = new Button(resources.getString("ExtraSettings"));

            playButton.setOnAction(e -> controllerClass.startSim());
            speedUpButton.setOnAction(e -> controllerClass.speedUp());
            slowDownButton.setOnAction(e -> controllerClass.slowDown());
            pauseButton.setOnAction(e -> controllerClass.stop());
            stepButton.setOnAction(e -> controllerClass.step());


            row.getChildren().addAll(playButton, speedUpButton, slowDownButton, pauseButton,
                    stepButton, extraSettingsButton);
            row.setAlignment(Pos.CENTER);
            return row;
        }

        private HBox secondButtonRow() {
            HBox row = new HBox(10);

            Button exitButton = new Button(resources.getString("ExitSimulation"));
            Button showGraphButton = new Button(resources.getString("ShowGraph"));
            Button saveButton = new Button(resources.getString("SaveSim"));
            Button loadAdditionalButton = new Button(resources.getString("LoadAdditionalSimulation"));

            exitButton.setOnAction(e -> controllerClass.exitSimulation());
            showGraphButton.setOnAction(e -> controllerClass.showVisualization());
            saveButton.setOnAction(e -> controllerClass.saveSimulationToDisk());
            loadAdditionalButton.setOnAction(e -> controllerClass.openAdditionalSimulation());

            row.getChildren().addAll(exitButton, showGraphButton, saveButton, loadAdditionalButton);
            row.setAlignment(Pos.CENTER);
            return row;
        }

        private void buildScene() {
            ObservableList<Node> rootChildren = this.root.getChildren();
            // add graph
            rootChildren.add(this.graph.getNode());

            VBox rows = new VBox(10);
            HBox rowOne = firstButtonRow();
            HBox rowTwo = secondButtonRow();

            rows.getChildren().addAll(rowTwo, rowOne);
            rows.setAlignment(Pos.CENTER);
            rows.setPrefWidth(this.WIDTH);
            rows.setTranslateY(this.HEIGHT - 80.0);

            rootChildren.add(rows);
        }

    }




}