package cellsociety;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public class Simulation{
  private Scene scene;
  private Group root;


  private double GRIDSTARTINGX;
  private double GRIDSTARTINGY;
  private double GRIDHEIGHT;
  private double GRIDWIDTH;
  private int GRIDCOLS;
  private int GIDROWS;
  private double GRIDSTROKEWIDTH;

  private SimulationType simtype;
  public Simulation(){
  }

  private Scene setUpSimulation(int width, int height, Paint background){
    root = new Group();
    CellState[][] initialStates = getInitialConfig();
    Grid newgrid = new Grid(GRIDSTARTINGX, GRIDSTARTINGY,GIDROWS, GRIDCOLS, GRIDWIDTH, GRIDHEIGHT,  initialStates, GRIDSTROKEWIDTH);
    // How to add newgrid to root.
    root.getChildren().add(newgrid);
    scene = new Scene(root, width, height, background);
    return scene;
  }
  private CellState[][] getInitialConfig(){
   // RETURN  A LIST OF STATE OF EACH CELL FROM CONFIGURATION OR GRID
  }
  private void step(CellState[][]){
    SimulationType.runAlgorithm()
  }
  private void getParameter(){

  }
  private void setParameter(){

  }
  private void start(){

  }
  private void stop(){

  }
  private void save(){

  }
  private void load(){

  }
  private void speedUp(){

  }
  private void slowDown(){
    
  }
}
