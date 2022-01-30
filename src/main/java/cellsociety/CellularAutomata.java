package cellsociety;


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
  private double GRIDSTARTINGY = 10 ;
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
  public CellularAutomata(){
  }

  protected Scene setUpSimulation(int width, int height, Paint background){
    runVal = true;
    root = new Group();
    CellState[][] initialStates = getInitialConfig();
    Grid newgrid = new Grid(GRIDSTARTINGX, GRIDSTARTINGY,GIDROWS, GRIDCOLS, GRIDWIDTH, GRIDHEIGHT,  initialStates, GRIDSTROKEWIDTH);

    startButton = new Button();
    stopButton= new Button();
    speedUp = new Button();
    slowDown= new Button();
    loadButton = new Button();
    saveButton = new Button();

    startButton.setText("Start");
    stopButton.setText("Stop");
    speedUp.setText(">>");
    slowDown.setText("<<");
    loadButton.setText("Load");
    saveButton.setText("Save");

    startButton.setOnAction(this);
    stopButton.setOnAction(this);
    speedUp.setOnAction(this);
    slowDown.setOnAction(this);
    loadButton.setOnAction(this);
    saveButton.setOnAction(this);

    startButton.setLayoutX();
    startButton.setLayoutY();
    stopButton.setLayoutX();
    stopButton.setLayoutY();
    saveButton.setLayoutX();
    saveButton.setLayoutY();
    loadButton.setLayoutX();
    loadButton.setLayoutY();
    speedUp.setLayoutX();
    speedUp.setLayoutY();
    slowDown.setLayoutX();
    slowDown.setLayoutY();

    // How to add newgrid to root.
    for(Rectangle r: newgrid.getRects()) {
      root.getChildren().add(r);
    }
    root.getChildren().add(startButton);
    root.getChildren().add(stopButton);
    root.getChildren().add(speedUp);
    root.getChildren().add(slowDown);
    root.getChildren().add(loadButton);
    root.getChildren().add(saveButton);

    scene = new Scene(root, width, height, background);

    //setting up the animation
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
    animation.play();



    return scene;
  }
  private CellState[][] getInitialConfig(){
   // RETURN  A LIST OF STATE OF EACH CELL FROM CONFIGURATION OR GRID
  }
  private void step(double elapsedTime){
    //method, updates all cells according to simtype
    //
    if (runVal){
      runStates.runAlgorithm
    }

  }
  private void getParameter(){

  }
  private void setParameter(){

  }
  private void start(){
    runVal = true;

  }
  private void stop(){
    runVal = false;
  }
  private void save(){
    stop();
    //reverse of creating grid from parsed file
  }
  private void load(){
    stop();
    setUpSimulation(Main.SIZE, Main.SIZE, Main.BACKGROUND);

  }
  private void speedUp(){
    FRAMES_PER_SECOND += 20;
  }
  private void slowDown(){
    FRAMES_PER_SECOND -= 20;
  }

  @Override
  public void handle(ActionEvent actionEvent) {
    if(actionEvent.getSource() == startButton){
      start();
    }
    else if(actionEvent.getSource() == stopButton) {
      stop();
    }
    else if(actionEvent.getSource() == speedUp) {
      speedUp();
    }
    else if(actionEvent.getSource() == slowDown) {
      slowDown();
    }
    else if(actionEvent.getSource() == loadButton) {
      load();
    }
    else if(actionEvent.getSource() == saveButton) {
      save();
    }
  }
}
