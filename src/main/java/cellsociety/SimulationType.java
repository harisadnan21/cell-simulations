package cellsociety;

import java.util.HashMap;
import java.util.Map;

public abstract class SimulationType {
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