package cellsociety;

import java.util.HashMap;
import java.util.Map;

public abstract class SimulationType {
    public static final int GAME_OF_LIFE = 0;
    public static final int PERCOLATION = 1;
    public static final int SCHELLING_SEGREGATION = 2;
    public static final int SPREADING_OF_FIRE = 3;
    public static final int WATOR = 4;

    private Map<String, String> simulationParameters;

    public SimulationType() {
        simulationParameters = new HashMap<>();

        readXML();

        setUpSimulationParameters();
    }

    /**
     * Initialize specific parameters for subclasses from values in simulationParameters.
     *
     * This method assumes that simulationParameters has already been populated from XML file.
     */
    protected abstract void setUpSimulationParameters();

    public abstract void runAlgorithm();

    private void readXML() {

    }




}