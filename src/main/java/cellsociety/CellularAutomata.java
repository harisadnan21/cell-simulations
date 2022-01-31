package cellsociety;


import cellsociety.CellState.GameOfLifeState;
import cellsociety.CellState.PercolationState;
import cellsociety.CellState.SchellingSegregationState;
import cellsociety.CellState.SpreadingOfFireState;
import cellsociety.CellState.WaTorState;
import java.io.File;
import java.util.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CellularAutomata implements EventHandler<ActionEvent> {

  private Scene scene;
  private Group root;

//>>>>>>> master:src/main/java/cellsociety/Simulation.java

  private double GRIDSTARTINGX = 10;
  private double GRIDSTARTINGY = 10;
  private double GRIDHEIGHT;
  private double GRIDWIDTH;
  private int GRIDCOLS;
  private int GIDROWS;
  private double GRIDSTROKEWIDTH;
  private Button startButton;
  private Button stopButton;
  private Button speedUp;
  private Button slowDown;
  private Button loadButton;
  private Button saveButton;
  public static int FRAMES_PER_SECOND = 60;
  public static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private boolean runVal;

  private CellularAutomataAlgorithm simtype;

  public CellularAutomata() {

  }

  protected Scene setUpSimulation(int width, int height, Paint background) {

    // Get SimulationData record from XML
    File configFile = new File("./data/exampleconfig.xml");
    SimulationData simulationData = new ConfigurationXMLParser(
        SimulationData.DATA_TYPE).getSimulationData(configFile);

    runVal = true;
    root = new Group();

    // Initialize Grid and GridView with starting config
    CellState[][] initialStates = getInitialConfig(simulationData.startingConfig(),
        simulationData.numRows(), simulationData.numColumns(), simulationData.simulationType());

    grid = new Grid(simulationData.numRows(), simulationData.numColumns(), initialStates, simulationData);
    gridView = new GridView(width, height, simulationData.numRows(),
        simulationData.numColumns());
    gridView.updateCells(grid.getCells());
    gridView.setLayoutX(0);
    gridView.setLayoutY(0);

    root.getChildren().add(gridView);

//    switch(simulationData.simulationType()) {
//      case CellularAutomataAlgorithm.GAME_OF_LIFE -> simulation = new GameOfLife(simulationData);
//      case CellularAutomataAlgorithm.PERCOLATION -> simulation = new Percolation(simulationData);
//      case CellularAutomataAlgorithm.SCHELLING_SEGREGATION -> simulation = new SchellingSegregation(simulationData);
//      case CellularAutomataAlgorithm.SPREADING_OF_FIRE -> simulation = new SpreadingOfFire(simulationData);
//      case CellularAutomataAlgorithm.WATOR -> simulation = new WaTor(simulationData);
//    }

//    startButton = new Button();
//    stopButton = new Button();
//    speedUp = new Button();
//    slowDown = new Button();
//    loadButton = new Button();
//    saveButton = new Button();
//
//    startButton.setText("Start");
//    stopButton.setText("Stop");
//    speedUp.setText(">>");
//    slowDown.setText("<<");
//    loadButton.setText("Load");
//    saveButton.setText("Save");
//
//    startButton.setOnAction(this);
//    stopButton.setOnAction(this);
//    speedUp.setOnAction(this);
//    slowDown.setOnAction(this);
//    loadButton.setOnAction(this);
//    saveButton.setOnAction(this);
//
//    startButton.setLayoutX();
//    startButton.setLayoutY();
//    stopButton.setLayoutX();
//    stopButton.setLayoutY();
//    saveButton.setLayoutX();
//    saveButton.setLayoutY();
//    loadButton.setLayoutX();
//    loadButton.setLayoutY();
//    speedUp.setLayoutX();
//    speedUp.setLayoutY();
//    slowDown.setLayoutX();
//    slowDown.setLayoutY();
//
//    root.getChildren().add(startButton);
//    root.getChildren().add(stopButton);
//    root.getChildren().add(speedUp);
//    root.getChildren().add(slowDown);
//    root.getChildren().add(loadButton);
//    root.getChildren().add(saveButton);

    scene = new Scene(root, width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    animation.play();

    return scene;
  }

  private CellState[][] getInitialConfig(String configString, int numRows, int numColumns,
      int algorithmType) {

    CellState[][] cellConfig = new CellState[numRows][numColumns];
    String[] initialStates = configString.split(" ");
//    for(String s : initialStates) {
//      System.out.println(s + " ");
//    }
    CellState[] possibleStates = new CellState[0];

    switch (algorithmType) {
      case CellularAutomataAlgorithm.GAME_OF_LIFE -> possibleStates = GameOfLifeState.values();
      case CellularAutomataAlgorithm.PERCOLATION -> possibleStates = PercolationState.values();
      case CellularAutomataAlgorithm.SCHELLING_SEGREGATION -> possibleStates = SchellingSegregationState.values();
      case CellularAutomataAlgorithm.SPREADING_OF_FIRE -> possibleStates = SpreadingOfFireState.values();
      case CellularAutomataAlgorithm.WATOR -> possibleStates = WaTorState.values();
    }

    for (int i = 0; i < cellConfig.length; i++) {
      for (int j = 0; j < cellConfig[0].length; j++) {
        cellConfig[i][j] = possibleStates[Integer.parseInt(initialStates[i * cellConfig[0].length + j])];
      }
    }

    return cellConfig;
  }

  private void step(double elapsedTime) {
    //method, updates all cells according to simtype
    //
    if (runVal) {
      //runStates.runAlgorithm
    }

  }

  private void getParameter() {

  }

  private void setParameter() {

  }

  private void start() {
    runVal = true;

  }

  private void stop() {
    runVal = false;
  }

  private void save() {
    stop();
    //reverse of creating grid from parsed file
  }

  private void load() {
    stop();
    setUpSimulation(Main.SIZE, Main.SIZE, Main.BACKGROUND);

  }

  private void speedUp() {
    FRAMES_PER_SECOND += 20;
  }

  private void slowDown() {
    FRAMES_PER_SECOND -= 20;
  }

  @Override
  public void handle(ActionEvent actionEvent) {
    if (actionEvent.getSource() == startButton) {
      start();
    } else if (actionEvent.getSource() == stopButton) {
      stop();
    } else if (actionEvent.getSource() == speedUp) {
      speedUp();
    } else if (actionEvent.getSource() == slowDown) {
      slowDown();
    } else if (actionEvent.getSource() == loadButton) {
      load();
    } else if (actionEvent.getSource() == saveButton) {
      save();
    }
  }
}
